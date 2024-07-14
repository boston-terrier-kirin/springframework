package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;

import java.util.List;

public interface PostService {
    public List<PostDto> findAllPosts();

    public void createPost(PostDto postDto);

    public PostDto findPostById(Long id);

    public void updatePost(PostDto postDto);

    public void deletePost(Long id);

    public PostDto findPostByUrl(String url);

    public List<PostDto> searchPosts(String query);
}
