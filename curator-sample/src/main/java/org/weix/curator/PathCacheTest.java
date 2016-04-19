package org.weix.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author weixing
 */
public class PathCacheTest {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .retryPolicy(new ExponentialBackoffRetry(3000, 5))
                .build();

        final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/test", true);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println(pathChildrenCacheEvent);
                System.out.println(pathChildrenCache.getCurrentData());
            }
        });
        curatorFramework.start();
        pathChildrenCache.start();
        System.out.println(pathChildrenCache.getCurrentData());

        System.in.read();

        pathChildrenCache.close();
        curatorFramework.close();
    }
}
