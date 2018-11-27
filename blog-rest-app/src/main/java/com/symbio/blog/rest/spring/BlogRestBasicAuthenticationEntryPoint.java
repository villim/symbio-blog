package com.symbio.blog.rest.spring;

import com.google.gson.Gson;
import com.symbio.blog.domain.exception.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class BlogRestBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private static final String BAD_CREDENTIAL_ERROR = "incorrect username, password or status is inactive!";

    @Autowired
    private Gson gson;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        response.addHeader("Content-Type", "application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter writer = response.getWriter();
        writer.println(gson.toJson(new ErrorMessage("Bad Credential Error", BAD_CREDENTIAL_ERROR)));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("BLOG");
        super.afterPropertiesSet();
    }

}
