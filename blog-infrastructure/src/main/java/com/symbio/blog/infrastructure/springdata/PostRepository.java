package com.symbio.blog.infrastructure.springdata;

import com.symbio.blog.domain.post.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Post findByTitle(String title);

    //@Cacheable(key = "#root.method.name+'-'+#p0")
    Post findById(long id);

    //@Cacheable(key = "#root.method.name+'-'+#p0")
    List<Post> findByUserId(long userId);

    //@Cacheable(key = "#root.method.name+'-'+#p0+'-'+#p1")
    @Query(value = "select * from post where title like '%:text%' or body like '%:text%'", nativeQuery = true)
    //TODO: nativeQuery not working with H2DB, need more time researching
    List<Post> searchWithTitleOrBody(@Param("text") String text);
}
