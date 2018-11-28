package com.symbio.blog.rest.service.post;

import com.symbio.blog.domain.exception.BlogServiceException;
import com.symbio.blog.domain.exception.BlogValidationException;
import com.symbio.blog.domain.post.Post;
import com.symbio.blog.infrastructure.springdata.PostRepository;
import com.symbio.blog.rest.schema.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;


    @Override
    public Post getById(long id) {
        return this.postRepository.findById(id);
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
        return this.postRepository.searchWithTitleOrBody(text);
    }


    @Override
    public List<Post> search(long userId) {
        return this.postRepository.findByUserId(userId);
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
