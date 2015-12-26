package org.weixing.spring.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.weixing.spring.mybatis.service.UserService;

import javax.sql.DataSource;

/**
 * @author weixing
 */
@SpringBootApplication
public class Application implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    MyBatisConfiguration myBatisConfiguration;

    @Autowired
    UserService userService;

    @Bean
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        if (StringUtils.hasText(myBatisConfiguration.getConfig())) {
            factory.setConfigLocation(
                    resourceLoader.getResource(myBatisConfiguration.getConfig()));
        }

        return new SqlSessionTemplate(factory.getObject(), ExecutorType.SIMPLE);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println(userService.getUsers());
    }
}
