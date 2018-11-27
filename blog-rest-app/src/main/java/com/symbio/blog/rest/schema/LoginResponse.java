package com.symbio.blog.rest.schema;

public class LoginResponse {

    private String xAuthToken;

    public LoginResponse(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }

    @Override
    public String toString() {
        return "LoginResponse [xAuthToken=" + xAuthToken + "]";
    }

}
