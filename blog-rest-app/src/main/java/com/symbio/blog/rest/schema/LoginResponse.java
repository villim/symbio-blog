package com.symbio.blog.rest.schema;

public class LoginResponse {

    private String xAuthToken;

    public LoginResponse() {
    }

    public LoginResponse(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }

    public String getxAuthToken() {
        return xAuthToken;
    }

    public void setxAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }

    @Override
    public String toString() {
        return "LoginResponse [xAuthToken=" + xAuthToken + "]";
    }

}
