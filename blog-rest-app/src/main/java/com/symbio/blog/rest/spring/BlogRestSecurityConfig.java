package com.symbio.blog.rest.spring;

import com.symbio.blog.domain.user.UserRoleConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class BlogRestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BlogAuthenticationProvider blogAuthenticationProvider;

    @Autowired
    private BlogRestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private BlogRestBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("ROLE_ADMIN"); // Keep for testing
        auth.authenticationProvider(blogAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and().authorizeRequests()
                .anyRequest().access("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_ANONYMOUS + "','" + UserRoleConstant.ROLE_USER + "')")
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);

    }

}
