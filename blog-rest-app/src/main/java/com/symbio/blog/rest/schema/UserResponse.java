package com.symbio.blog.rest.schema;

public class UserResponse {

    private String userId;

    public UserResponse(String userId) {
        this.userId = userId;
    }

    public UserResponse() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
