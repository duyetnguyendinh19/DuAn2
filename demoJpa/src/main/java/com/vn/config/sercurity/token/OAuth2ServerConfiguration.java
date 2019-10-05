//package com.vn.config.sercurity.token;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.token.DefaultToken;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class OAuth2ServerConfiguration {
//
//    public static final String RESOURCE_ID = "demoJPA.io";
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//        @Autowired
//        UserDetailsService authUserDetailsService;
//
//        @Autowired
//        PasswordEncoder passwordEncoder;
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resourceServerConfigurer) {
//            resourceServerConfigurer
//                    .stateless(true)
//                    .resourceId(RESOURCE_ID);
//        }
//
//        @Override
//        public void configure(HttpSecurity security) throws Exception {
//            security
//                    .authorizeRequests()
//                    .regexMatchers(HttpMethod.POST, "/oauth/expire")
//                    .permitAll()
//                    .antMatchers("/admin").hasAnyRole("ADMIN")
//                    .antMatchers("/user").hasAnyRole("USER")
//                    .anyRequest()
//                    .permitAll()
//                    .and()
//                    .csrf().disable()
//                    .userDetailsService(this.authUserDetailsService)
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        }
//    }
//
//    @Configuration
//    @EnableAuthorizationServer
//    protected static class AuthorizationServerSecurityConfiguration extends AuthorizationServerConfigurerAdapter {
//
//        @Autowired
//        private DataSource dataSource;
//
//        @Autowired
//        private PasswordEncoder passwordEncoder;
//
//        @Autowired
//        AuthenticationManager authenticationManager;
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) {
//            endpointsConfigurer
//                    .tokenServices(tokenServices())
//                    .authenticationManager(this.authenticationManager);
//        }
//
//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer configurer) throws Exception {
//            configurer.checkTokenAccess("permitAll()");
//            configurer.tokenKeyAccess("permitAll()");
//            configurer.realm(RESOURCE_ID + "/client");
//        }
//
//        /**
//         * 	cd8aa153 - 682f3fe0 //Basic Y2Q4YWExNTM6NjgyZjNmZTA=
//         *	aa486ce9 - a2257b2c //Basic YWE0ODZjZTk6YTIyNTdiMmM=
//         */
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer client) throws Exception {
//            client
//                    .inMemory()
//                    .withClient("cd8aa153")
//                        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                        .scopes("read", "write", "trust")
//                        .resourceIds(RESOURCE_ID)
//                        .secret("682f3fe0")
//                    .and()
//                    .withClient("aa486ce9")
//                        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                        .scopes("read", "write", "trust")
//                        .resourceIds(RESOURCE_ID)
//                        .secret("a2257b2c");
//        }
//
//        @Bean
//        @Primary
//        public DefaultTokenServices tokenServices() {
//            DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//            defaultTokenServices.setAccessTokenValiditySeconds(0);
//            defaultTokenServices.setRefreshTokenValiditySeconds(0);
//            defaultTokenServices.setSupportRefreshToken(false);
//            defaultTokenServices.setReuseRefreshToken(false);
//            defaultTokenServices.setTokenStore(tokenStore());
//            return defaultTokenServices;
//        }
//
//        @Bean
//        @Primary
//        public TokenStore tokenStore() {
//            JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(this.dataSource);
//            jdbcTokenStore.setAuthenticationKeyGenerator(new SingleAuthenticationKeyGenerator());
//            return jdbcTokenStore;
//        }
//    }
//}
//
