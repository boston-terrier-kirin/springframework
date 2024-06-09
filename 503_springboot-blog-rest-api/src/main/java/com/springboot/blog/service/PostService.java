package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponseDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    public PostResponseDto getAllPosts(int pageNo, int pageSize);

    public PostDto getPostById(Long id);

    public PostDto updatePostById(Long id, PostDto postDto);

    public PostDto deletePostById(Long id);
}
