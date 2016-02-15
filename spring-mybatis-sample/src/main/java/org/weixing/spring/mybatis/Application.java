package org.weixing.spring.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.weixing.spring.mybatis.service.UserService;

/**
 * @author weixing
 */
@SpringBootApplication
public class Application implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println(userService.getUsers());
    }
}
