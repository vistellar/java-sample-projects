package org.weix.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

/**
 * @author weixing
 */
public class CuratorDistributedSet implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(CuratorDistributedSet.class);

    public interface AddEventListener {
        public void process(String item);
    }

    public interface RemoveEventListener {
        public void process(String item);
    }

    private CuratorFramework curatorClient;
    private String path;

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            sync();
        }
    };

    private Set<String> internal = new HashSet<String>() {
        @Override
        public boolean add(String item) {
            synchronized (addEventListeners) {
                for (AddEventListener addEventListener : addEventListeners) {
                    addEventListener.process(item);
                }
            }
            return super.add(item);
        }

        @Override
        public boolean remove(Object item) {
            synchronized (removeEventListeners) {
                for (RemoveEventListener removeEventListener : removeEventListeners) {
                    removeEventListener.process(item.toString());
                }
            }
            return super.remove(item);
        }
    };
    private List<AddEventListener> addEventListeners
            = Collections.synchronizedList(new ArrayList<AddEventListener>());
    private List<RemoveEventListener> removeEventListeners
            = Collections.synchronizedList(new ArrayList<RemoveEventListener>());

    public CuratorDistributedSet(int capacity, CuratorFramework curatorClient, String path) {
        this.curatorClient = curatorClient;
        this.path = path;
    }

    public void start() {
        curatorClient.start();
        rebuild();
    }

    public List<AddEventListener> getAddEventListeners() {
        return addEventListeners;
    }

    public List<RemoveEventListener> getRemoveEventListeners() {
        return removeEventListeners;
    }

    public int size() {
        return internal.size();
    }

    public boolean contains(String item) {
        return internal.contains(item);
    }

    public Iterator<String> iterator() {
        return internal.iterator();
    }

    public boolean add(String item) {
        if (internal.contains(item)) {
            return false;
        }

        try {
            curatorClient.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(item);
            return true;
        } catch (KeeperException.NodeExistsException ignore) {
            return false;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    public boolean remove(Object item) {
        try {
            curatorClient.delete()
                    .deletingChildrenIfNeeded()
                    .forPath(item.toString());
            return true;
        } catch (KeeperException.NoNodeException e) {
            return false;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    public void rebuild() {
        synchronized (addEventListeners) {
            synchronized (removeEventListeners) {
                List<AddEventListener> addEventListenersRef = addEventListeners;
                List<RemoveEventListener> removeEventListenersRef = removeEventListeners;

                addEventListeners = Collections.emptyList();
                removeEventListeners = Collections.emptyList();

                sync();

                addEventListeners = addEventListenersRef;
                removeEventListeners = removeEventListenersRef;
            }
        }
    }

    private synchronized void sync() {
        List<String> children = null;
        try {
            children = curatorClient.getChildren()
                    .usingWatcher(watcher)
                    .forPath(path);
        } catch (Exception e) {
            logger.error("", e);
        }

        Set<String> removeItems = new HashSet<>(internal);
        removeItems.removeAll(children);
        internal.removeAll(removeItems);
        logger.debug("{}", removeItems);

        Set<String> addItems = new HashSet<>(children);
        addItems.removeAll(internal);
        internal.addAll(addItems);
        logger.debug("{}", addItems);
    }

    @Override
    public void close() throws IOException {
        curatorClient.close();
    }
}
