package com.vn.config;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager",basePackages = {"com.vn.repository"})
@EnableTransactionManagement
public class JpaPersistenceConfig {
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS = "hibernate.generate_statistics";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA = "hibernate.default_schema";

    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.vn.jpa";
//    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN_FBG = "vn.hathanhtelecom.footballgame.jpa";
//    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN_FBG01 = "vn.hathanhtelecom.fbgame01.jpa";
    // fix hibernate properties
    private static final String PROPERTY_ZERO_DATETIME_BEHAVIOR = "hibernate.connection.zeroDateTimeBehavior";

    @Autowired
    private DataSource dataSource;

    /**
     * JDNI datasource
     */
//    @Value("${jndi.datasource}")
//    private String jndiDatasource;

    /**
     * hibernate properties
     */
    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.format_sql}")
    private String hibernateFormatSql;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;


    @Value("${hibernate.generate_statistics}")
    private String hibernateGenerateStatistics;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;

    @Value("${hibernate.default_schema}")
    private String hibernateDefaultSchema;


    @Bean("transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean("entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(this.dataSource);

        emf.setPackagesToScan(new String[] { PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN});
        emf.setPersistenceUnitName("entityManagerFactory");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(additionalProperties());
        return emf;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }


    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, this.hibernateDialect);
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, this.hibernateShowSql);
        properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, this.hibernateShowSql);
        properties.put(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS, this.hibernateGenerateStatistics);
        properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, this.hibernateHbm2ddlAuto);
        if (!Strings.isNullOrEmpty(hibernateDefaultSchema)) {
            properties.put(PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA, this.hibernateDefaultSchema);
        }
        // fix properties
        properties.put(PROPERTY_ZERO_DATETIME_BEHAVIOR, "convertToNull");
        return properties;
    }
}
