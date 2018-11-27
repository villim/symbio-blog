package com.symbio.blog.rest.spring;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(BlogRestSecurityConfig.class);
    }

}