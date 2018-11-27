package com.symbio.blog.rest.service.user;

import com.symbio.blog.domain.exception.BlogServiceException;
import com.symbio.blog.domain.exception.BlogValidationException;
import com.symbio.blog.domain.user.User;
import com.symbio.blog.domain.user.UserRoleConstant;
import com.symbio.blog.infrastructure.springdata.UserRepository;
import com.symbio.blog.rest.schema.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Optional<User> getById(long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public long create(UserRequest userRequest) {
        if (checkExistedUser(userRequest.getName()) != null) {
            throw new BlogValidationException("User with name [" + userRequest.getName() + "] already existed.");
        }
        return saveUser(userRequest, null);
    }


    @Override
    public long update(UserRequest userRequest) {
        User existedUser = checkExistedUser(userRequest.getName());
        if (existedUser == null) {
            throw new BlogValidationException("User with name [" + userRequest.getName() + "] not existed.");
        }
        return saveUser(userRequest, existedUser);
    }

    @Override
    public void delete(long id) {
        this.userRepository.delete(this.userRepository.findById(id).get());
    }

    private User checkExistedUser(String name) {
        User existedUser = this.userRepository.findByName(name);
        if (existedUser != null && existedUser.getId() != 0) {
            return existedUser;
        } else {
            return null;
        }
    }

    private User buildUser(User existedUser, UserRequest userRequest) {
        User user = existedUser == null ? new User() : existedUser;
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setRoles(UserRoleConstant.ROLE_USER + ";" + UserRoleConstant.ROLE_POST + ";" + UserRoleConstant.ROLE_COMMENT);
        user.setModifiedDate(new Date());
        return user;
    }

    private long saveUser(UserRequest userRequest, User existedUser) {
        try {
            return this.userRepository.save(buildUser(existedUser, userRequest)).getId();
        } catch (Exception e) {
            throw new BlogServiceException(e.getMessage());
        }
    }
}
