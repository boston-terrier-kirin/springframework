package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    public void createComment(String postUrl, CommentDto commentDto);

    public List<CommentDto> findAllComments();

    public void deleteComment(Long commentId);
}
