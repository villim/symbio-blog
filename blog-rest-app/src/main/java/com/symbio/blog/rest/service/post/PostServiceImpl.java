package com.symbio.blog.rest.service.post;

import com.symbio.blog.domain.exception.BlogServiceException;
import com.symbio.blog.domain.exception.BlogValidationException;
import com.symbio.blog.domain.post.Post;
import com.symbio.blog.infrastructure.springdata.PostRepository;
import com.symbio.blog.rest.schema.PostRequest;
import com.symbio.blog.rest.service.cache.CacheManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CacheManagerService cacheManagerService;


    @Override
    public Post getById(long id) {
        String key = "PostService-getById-id-" + id;
        Object obj = this.cacheManagerService.getFromCache(key);
        if (obj != null) {
            return (Post) obj;
        } else {
            Post post = this.postRepository.findById(id);
            this.cacheManagerService.putIntoCache(key, post);
            return post;
        }
    }

    @Override
    public void delete(long id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public long publish(PostRequest postRequest) {
        Post existedPost = checkExistedPost(postRequest.getTitle());
        if (existedPost != null) {
            throw new BlogValidationException("Post with name [" + postRequest.getTitle() + "] already existed.");
        }
        return this.postRepository.save(buildPost(null, postRequest)).getId();
    }


    @Override
    public long update(PostRequest postRequest) {
        Post existedPost = checkExistedPost(postRequest.getTitle());
        if (existedPost == null) {
            throw new BlogValidationException("Post with name [" + postRequest.getTitle() + "] not existed.");
        }
        return this.postRepository.save(buildPost(existedPost, postRequest)).getId();
    }


    @Override
    public List<Post> search(int limit, String text) {
        String key = "PostService-search-limit-" + limit + "-userId-" + text;
        Object obj = this.cacheManagerService.getFromCache(key);
        if (obj != null) {
            return (List<Post>) obj;
        } else {
            List<Post> posts = this.postRepository.searchWithTitleOrBody(text);
            this.cacheManagerService.putIntoCache(key, posts);
            return posts;
        }
    }


    @Override
    public List<Post> search(long userId) {
        String key = "PostService-search-userId" + userId;
        Object obj = this.cacheManagerService.getFromCache(key);
        if (obj != null) {
            return (List<Post>) obj;
        } else {
            List<Post> posts = this.postRepository.findByUserId(userId);
            this.cacheManagerService.putIntoCache(key, posts);
            return posts;
        }
    }

    private Post checkExistedPost(String title) {
        Post existedPost = this.postRepository.findByTitle(title);
        if (existedPost != null && existedPost.getId() != 0) {
            return existedPost;
        } else {
            return null;
        }
    }

    private Post buildPost(Post existedPost, PostRequest postRequest) {
        Post post = existedPost == null ? new Post() : existedPost;
        post.setTitle(postRequest.getTitle());
        post.setBody(postRequest.getBody());
        post.setModifiedDate(new Date());
        post.setUserId(postRequest.getUserId());
        return post;
    }


    private long savePost(PostRequest postRequest, Post existedPost) {
        try {
            return this.postRepository.save(buildPost(existedPost, postRequest)).getId();
        } catch (Exception e) {
            throw new BlogServiceException(e.getMessage());
        }
    }

}
