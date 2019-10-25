package com.vn.config;

import com.vn.common.AppUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = { "com.vn" })
@ImportResource(value = { "classpath:applicationContext.xml" })
public class AppConfig {

    private static Logger LOG = LoggerFactory.getLogger(AppConfig.class);
    /**
     * JDBC properties
     */
    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.max-total-connection}")
    private int jdbcMaxTotalConnection;

    @Value("${jdbc.max-idle-connection}")
    private int jdbcMaxIdleConnection;

    @Value("${jdbc.max-init-connection}")
    private int jdbcInitConnection;

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(this.jdbcDriverClassName);
        basicDataSource.setUrl(this.jdbcUrl);
        basicDataSource.setUsername(AppUtil.decryptPropertyValue(this.jdbcUsername));
        basicDataSource.setPassword(AppUtil.decryptPropertyValue(this.jdbcPassword));
        basicDataSource.setInitialSize(this.jdbcInitConnection);
        basicDataSource.setMaxIdle(this.jdbcMaxIdleConnection);
        basicDataSource.setMaxTotal(this.jdbcMaxTotalConnection);
        return basicDataSource;
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
