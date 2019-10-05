package com.vn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PropertyConfig {

    private static final Resource[] DEV_PROPERTIES = new ClassPathResource[] { new ClassPathResource("application.properties")};
    private static final String ENDCODE_UTF8 = "UTF-8";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setFileEncoding(ENDCODE_UTF8);
        pspc.setIgnoreResourceNotFound(false);
        pspc.setIgnoreUnresolvablePlaceholders(false);
        pspc.setOrder(0);
        pspc.setLocalOverride(true);
        pspc.setLocations(DEV_PROPERTIES);
        return pspc;
    }

}
