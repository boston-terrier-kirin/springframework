package com.springboot.blog.mapper;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PostDto mapToDto(Post post) {
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    public Post mapToEntity(PostDto postDto) {
        Post post = this.modelMapper.map(postDto, Post.class);
        return post;
    }
}
