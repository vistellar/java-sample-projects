package org.weixing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weixing
 */
@SpringBootApplication
@ConfigurationProperties(prefix = "application")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
