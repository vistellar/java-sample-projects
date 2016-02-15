package org.weixing.spring.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author weixing
 */
@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class MyBatisConfiguration {
    private static final String DEFAULT_CONFIG_PATH = "classpath:/mybatis-config.xml";

    private String configPath = DEFAULT_CONFIG_PATH;

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    @Bean
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        if (StringUtils.hasText(configPath)) {
            factory.setConfigLocation(
                    resourceLoader.getResource(configPath));
        }

        return new SqlSessionTemplate(factory.getObject(), ExecutorType.SIMPLE);
    }
}
