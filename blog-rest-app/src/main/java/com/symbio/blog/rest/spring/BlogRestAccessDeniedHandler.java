package com.symbio.blog.rest.spring;

import com.google.gson.Gson;
import com.symbio.blog.domain.exception.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class BlogRestAccessDeniedHandler implements AccessDeniedHandler {

    private static final String PERMISSION_ERROR = "You do not have full permission for this method!";

    @Autowired
    private Gson gson;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=Blog ");
        response.addHeader("Content-Type", "application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter writer = response.getWriter();
        writer.println(gson.toJson(new ErrorMessage("Not Enough Permission Error", PERMISSION_ERROR)));
    }
}
