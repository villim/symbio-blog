package com.symbio.blog.infrastructure.springdata;

import com.symbio.blog.domain.post.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Post findByTitle(String title);


    // TODO: should use native SQL
    List<Post> findByTitleOrBody(String title, String body);
}
