package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/{postUrl}/comments")
    public String createComment(@PathVariable("postUrl") String postUrl,
                                @ModelAttribute("commentDto") @Valid CommentDto commentDto,
                                BindingResult bindingResult,
                                Model model) {

        PostDto postDto = this.postService.findPostByUrl(postUrl);

        if (bindingResult.hasErrors()) {
            model.addAttribute("postDto", postDto);
            model.addAttribute("commentDto", commentDto);
            return "blog/blog-post";
        }

        this.commentService.createComment(postUrl, commentDto);

        return "redirect:/post/" + postUrl;
    }
}
