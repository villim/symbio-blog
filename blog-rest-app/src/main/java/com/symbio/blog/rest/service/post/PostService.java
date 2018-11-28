package com.symbio.blog.rest.service.post;

import com.symbio.blog.domain.post.Post;
import com.symbio.blog.rest.schema.PostRequest;

import java.util.List;

public interface PostService {

    Post getById(long id);

    void delete(long id);

    long publish(PostRequest post);

    long update(PostRequest post);

    List<Post> search(int limit, String text);

    List<Post> search(long userId);

}
