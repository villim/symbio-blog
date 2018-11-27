package com.symbio.blog.rest.service.user;

import com.symbio.blog.domain.user.User;
import com.symbio.blog.rest.schema.UserRequest;

import java.util.Optional;

public interface UserService {

    Optional<User> getById(long id);

    long create(UserRequest userRequest);

    long update(UserRequest userRequest);

    void delete(long id);

}
