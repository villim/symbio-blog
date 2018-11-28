package com.symbio.blog.rest.schema;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class UserRequest {

    @NotNull(message = "Name can't be Null")
    @Size(max = 20, message = "Name should be less than 20 characters!")
    private String name;

    @NotNull(message = "Password can't be Null")
    @Size(max = 30, message = "Password should be less than 30 characters")
    private String password;

    @Size(max = 30, message = "EmailAddress should be less than 30 characters!")
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Email Address format invalid")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
