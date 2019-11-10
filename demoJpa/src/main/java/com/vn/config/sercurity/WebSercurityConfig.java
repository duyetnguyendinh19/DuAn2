package com.vn.config.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSercurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean("userLoginSuccessHandler")
    public AuthenticationSuccessHandler userLoginSuccessHandler() {
        return new AppAuthenticationSuccessHanding();
    }

    @Bean("customAuthenticationProvider")
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean("myPermissionEvaluator")
    public PermissionEvaluator myPermissionEvaluator() {
        return new MyPermissionEvaluator();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
    }

    @Override
    public void configure(WebSecurity security) throws Exception {
        security.ignoring().antMatchers("/webjars/**", "/resources/**", "/static/**", "/repository/**", "/assets/**",
                "/fonts/**","/","/home/**","/cart/**", "/main-img/**","/sub-img/**","/report/save.html","/payment/**","/account/save.html",
                "/bank/**","/vnpay-transaction-result/**","/reject/**","/report/saveFooter.html");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return super.userDetailsService();
//    };

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .antMatchers("/webjars/**", "/resources/**", "/assets/**", "/fonts/**", "/ws/**").permitAll()
//                .antMatchers("/fbuser/user-blocked.html").permitAll()
                .antMatchers("/**").hasAnyAuthority("Administrators","Staffs")
                .anyRequest().authenticated()
                .and()
                    .exceptionHandling().accessDeniedPage("/error/403.html?accessDinied=true")
                .and()
                    .headers().frameOptions().disable().and()
                .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginPage("/admin/login.html")
                    .failureUrl("/admin/login.html?authentication_error=true")
                    .loginProcessingUrl("/admin/login.html")
                    .successHandler(userLoginSuccessHandler())
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/admin/login.html?logout=true")
                    .logoutUrl("/logout.html")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout.html"))
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                    .sessionManagement()
                    .invalidSessionUrl("/admin/login.html?invalid=true")
                    .maximumSessions(-1)
                    .expiredUrl("/admin/login.html?exprired=true")
                    .maxSessionsPreventsLogin(false) //Không cho đăng nhập khi đã tồn tại session
                .and()
                    .enableSessionUrlRewriting(false)
                .and().csrf().disable();
    }
}
