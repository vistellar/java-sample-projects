package org.weixing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Autowired bean list.
 *
 * @author weixing
 */
@Component
public class BeanListRunner implements ApplicationRunner {

    public interface Task {
        public void run();
    }

    @Component
    public static class LocalTask implements Task {

        @Override
        public void run() {
            System.out.println("Local Task");
        }
    }

    @Component
    public static class RemoteTask implements Task {

        @Override
        public void run() {
            System.out.println("Remote Task");
        }
    }

    /**
     * @ConditionalOnProperty(value = {"null-task"})
     * valid only when key "null-task" exists in conf file
     *
     * @ConditionalOnProperty(value = {"null-task"}, matchIfMissing = true)
     * valid only when key "null-task" not exists in conf file
     *
     * @ConditionalOnProperty(value = {"null-task"}, havingValue = "test")
     * valid only when key "null-task" exists in conf file and value equal to "test"
     */
    @Component
    @ConditionalOnProperty(value = {"null-task"}, havingValue = "test")
    public static class NullTask implements Task {

        @Override
        public void run() {
            System.out.println("Null Task");
        }
    }

    private List<Task> tasks;

    @Autowired
    public BeanListRunner(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println(tasks.size());
        for (Task task : tasks) {
            task.run();
        }
    }
}
