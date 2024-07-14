package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, PostRepository postRepository, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {
        Post post = this.postRepository.findByUrl(postUrl).get();
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        this.commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> commentList = this.commentRepository.findAll();
        return commentList.stream()
                            .map(comment -> this.modelMapper.map(comment, CommentDto.class))
                            .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        this.commentRepository.deleteById(commentId);
    }
}
