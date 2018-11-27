package com.symbio.blog.rest.controller;

import com.google.gson.Gson;
import com.symbio.blog.domain.Version;
import com.symbio.blog.domain.post.Post;
import com.symbio.blog.domain.user.User;
import com.symbio.blog.domain.user.UserRoleConstant;
import com.symbio.blog.rest.schema.*;
import com.symbio.blog.rest.service.RequestValidator;
import com.symbio.blog.rest.service.post.PostService;
import com.symbio.blog.rest.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Valid;
import java.util.List;


@RestController
public class BlogRestController {

    private static final Logger LOG = LoggerFactory.getLogger(BlogRestController.class);

    @Autowired
    private Environment env;

    @Autowired
    private Gson gson;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


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
    @RequestMapping(method = RequestMethod.GET, value = "/v1/login", produces = "application/json")
    public LoginResponse login() {
        return new LoginResponse(RequestContextHolder.getRequestAttributes().getSessionId());
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
        return this.userService.getById(userId).get();
    }

    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "')")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/users/{id}", produces = "application/json")
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
    @RequestMapping(method = RequestMethod.GET, value = "/v1/posts/{id}", produces = "application/json")
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
        return this.postService.getById(postId).get();
    }

    /**
     * Only provide search by Id is not enough , We need implement search with filter
     */
    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_ANONYMOUS + "','" + UserRoleConstant.ROLE_USER + "')")
    @RequestMapping(method = RequestMethod.GET, value = "/v1/transactions", produces = "application/json")
    public List<Post> searchPost(@RequestParam(value = "limit", required = true) String limit,
                                 @RequestParam(value = "tile", required = false) String tile,
                                 @RequestParam(value = "body", required = false) String body) {

        return this.postService.search(tile, body);
    }

    @PreAuthorize("hasAnyRole('" + UserRoleConstant.ROLE_ADMIN + "','" + UserRoleConstant.ROLE_COMMENT + "')")
    @RequestMapping(method = RequestMethod.POST, value = "/v1/{postId}/comments", consumes = "application/json", produces = "application/json")
    public void publishComment(@Valid @RequestBody CommentRequest request) {

    }
}
