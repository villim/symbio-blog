package com.symbio.blog.rest.controller;

import com.symbio.blog.domain.Version;
import com.symbio.blog.domain.post.Post;
import com.symbio.blog.domain.user.User;
import com.symbio.blog.domain.user.UserRoleConstant;
import com.symbio.blog.rest.schema.*;
import com.symbio.blog.rest.service.RequestValidator;
import com.symbio.blog.rest.service.post.PostService;
import com.symbio.blog.rest.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class BlogRestController {


    @Autowired
    private Environment env;


    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    /**
     * Version API is for check deployed application version
     * Also a API can be used in monitor services
     */
    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "')")
    @RequestMapping(method = RequestMethod.GET, value = "/version", produces = "application/json")
    public Version version() {
        String projectVersion = env.getProperty("project.version", "");
        String gitVersion = env.getProperty("git.version", "");
        return new Version(projectVersion, gitVersion);
    }

    /**
     * login succeeded will return sessionId, which can be reused to reduce creating too much sessions
     *
     * @return jetty sessionId as xAuthToken
     */
    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_USER + "')")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/login")
    public LoginResponse login() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setxAuthToken(RequestContextHolder.getRequestAttributes().getSessionId());
        return loginResponse;
    }

    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_ANONYMOUS + "')")
    @RequestMapping(method = RequestMethod.POST, value = "/v1/users", consumes = "application/json", produces = "application/json")
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        RequestValidator.validateUserRequest(request);
        return new UserResponse((String.valueOf(this.userService.create(request))));
    }

    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_USER + "')")
    @RequestMapping(method = RequestMethod.PUT, value = "/v1/users", consumes = "application/json", produces = "application/json")
    public UserResponse updateUser(@Valid @RequestBody UserRequest request) {
        RequestValidator.validateUserRequest(request);
        return new UserResponse((String.valueOf(this.userService.update(request))));
    }

    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "')")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/users/{id}", produces = "application/json")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        Optional<User> user = this.userService.getById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/v1/users/{id}", produces = "application/json")
    public void deleteUserById(@PathVariable(value = "id") Long userId) {
        this.userService.delete(userId);
    }


    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_POST + "')")
    @RequestMapping(method = RequestMethod.POST, value = "/v1/posts", consumes = "application/json", produces = "application/json")
    public PostResponse publishPost(@Valid @RequestBody PostRequest request) {
        RequestValidator.validatePostRequest(request);
        return new PostResponse(String.valueOf(this.postService.publish(request)));
    }


    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_POST + "')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/v1/posts/{id}", produces = "application/json")
    public void deletePostById(@PathVariable(value = "id") Long postId) {
        this.postService.delete(postId);
    }


    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_POST + "')")
    @RequestMapping(method = RequestMethod.PUT, value = "/v1/posts", consumes = "application/json", produces = "application/json")
    public PostResponse updatePost(@Valid @RequestBody PostRequest request) {
        RequestValidator.validatePostRequest(request);
        return new PostResponse(String.valueOf(this.postService.update(request)));
    }


    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_ANONYMOUS + "','" + UserRoleConstant.ROLE_USER + "')")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/posts/{id}", produces = "application/json")
    public Post getPost(@PathVariable(value = "id") Long postId) {
        return this.postService.getById(postId);
    }

    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_ANONYMOUS + "','" + UserRoleConstant.ROLE_USER + "')")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/users/{id}/posts", produces = "application/json")
    public List<Post> getPostsOfUser(@PathVariable(value = "id") Long userId) {
        return this.postService.search(userId);
    }

    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_ANONYMOUS + "','" + UserRoleConstant.ROLE_USER + "')")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/posts", produces = "application/json")
    public List<Post> searchPost(@RequestParam(value = "limit", required = false) int limit,
                                 @RequestParam(value = "text", required = false) String text) {
        return this.postService.search(limit, text);
    }

//    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_COMMENT + "')")
//    @RequestMapping(method = RequestMethod.POST, value = "/v1/{postId}/comments", consumes = "application/json", produces = "application/json")
//    public void publishComment(@Valid @RequestBody CommentRequest request) {
//        // To be continue
//    }
}
