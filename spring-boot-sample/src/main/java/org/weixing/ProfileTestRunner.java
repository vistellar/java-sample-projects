package org.weixing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author weixing
 */
@Component
public class ProfileTestRunner implements ApplicationRunner {

    public interface Task {
        public void run();
    }

    @Component
    @Profile("default")
    public static class DefaultTask implements Task {

        @Override
        public void run() {
            System.out.println("Default Task");
        }
    }

    @Component
    @Profile("test")
    public static class TestTask implements Task {

        @Override
        public void run() {
            System.out.println("Test Task");
        }
    }

    @Value("${title}")
    private String title;

    @Autowired
    private Task task;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("ProfileTestRunner");
        task.run();
        System.out.println(title);
    }
}
