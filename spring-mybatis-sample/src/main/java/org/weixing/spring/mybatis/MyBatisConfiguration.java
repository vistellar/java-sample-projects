package org.weixing.spring.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author weixing
 */
@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class MyBatisConfiguration {

    private String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
