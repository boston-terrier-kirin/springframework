package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(ModelMapper modelMapper, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> postList = this.postRepository.findAll();
        return postList.stream()
                        .map(post -> this.modelMapper.map(post, PostDto.class))
                        .collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        Post post = this.modelMapper.map(postDto, Post.class);
        this.postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long id) {
        Post post = this.postRepository.findById(id).get();

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = this.modelMapper.map(postDto, Post.class);
        this.postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public PostDto findPostByUrl(String url) {
        Post post = this.postRepository.findByUrl(url).get();

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> postList = this.postRepository.searchPosts(query);
        return postList.stream()
                        .map(post -> this.modelMapper.map(post, PostDto.class))
                        .collect(Collectors.toList());
    }
}
