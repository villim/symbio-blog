package com.symbio.blog.rest.service.post;

import com.symbio.blog.domain.post.Post;
import com.symbio.blog.rest.schema.PostRequest;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> getById(long id);

    void delete(long id);

    long publish(PostRequest post);

    long update(PostRequest post);

    List<Post> search(String title, String body);

    List<Post> search(long userId);

}
