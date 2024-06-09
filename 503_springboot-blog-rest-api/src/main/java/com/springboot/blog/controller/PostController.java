package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponseDto;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto postResponse = this.postService.createPost(postDto);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponseDto> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        PostResponseDto postResponseDto = this.postService.getAllPosts(pageNo, pageSize);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable(name = "id") Long id) {
        PostDto postResponse = this.postService.getPostById(id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto postDto) {
        PostDto postResponse = this.postService.updatePostById(id, postDto);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable(name = "id") Long id) {
        PostDto postResponse = this.postService.deletePostById(id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
}
