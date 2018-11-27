package com.symbio.blog.rest.schema;

public class PostResponse {

    private String postId;

    public PostResponse(String postId) {
        this.postId = postId;
    }

    public PostResponse() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
