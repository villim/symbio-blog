package com.symbio.blog.domain.user;

public interface UserRoleConstant {

    String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Able to read posts, create new user
     */
    String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Able to update and delete user
     */
    String ROLE_USER = "ROLE_USER";

    /**
     * Able to publish post
     */
    String ROLE_POST = "ROLE_POST";

    /**
     * Able to publish comment
     */
    String ROLE_COMMENT = "ROLE_COMMENT";


}

