package com.symbio.blog.rest.schema;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class PostRequest {

    @NotNull(message = "UserId can't be Null")
    private long userId;


    @NotNull(message = "Title can't be Null")
    @Size(max = 100, message = "Title should be less than 100 characters")
    private String title;

    @Size(max = 500, message = "Title should be less than 100 characters")
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
