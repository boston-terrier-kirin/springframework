package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponseDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostDto createPost(PostDto postDto) {
        Post post = this.mapToEntity(postDto);
        Post newPost = this.postRepository.save(post);
        PostDto postResponse = this.mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.by(sortBy)
                        : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> page = this.postRepository.findAll(pageable);
        List<Post> postList = page.getContent();

        List<PostDto> postDtoList =
                postList.stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setContent(postDtoList);
        postResponseDto.setPageNo(pageNo);
        postResponseDto.setPageSize(pageSize);
        postResponseDto.setTotalElements(page.getTotalElements());
        postResponseDto.setTotalPages(page.getTotalPages());
        postResponseDto.setLastPage(page.isLast());

        return postResponseDto;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = this.postRepository
                            .findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return this.mapToDto(post);
    }

    @Override
    public PostDto updatePostById(Long id, PostDto postDto) {
        Post post = this.postRepository
                            .findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = this.postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public PostDto deletePostById(Long id) {
        Post post = this.postRepository
                            .findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        this.postRepository.deleteById(id);

        return mapToDto(post);
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = this.modelMapper.map(postDto, Post.class);
        return post;
    }
}
