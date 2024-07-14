package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Controller
public class PostController {

    private final MessageSource messageSource;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public PostController(MessageSource messageSource, PostService postService, CommentService commentService) {
        this.messageSource = messageSource;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/admin/posts")
    public String posts(Model model) {
        List<PostDto> postDtoList = this.postService.findAllPosts();
        model.addAttribute("postDtoList", postDtoList);

        return "admin/posts";
    }

    @GetMapping("/admin/posts/new")
    public String newPostForm(Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("postDto", postDto);

        return "admin/new-post";
    }

    @GetMapping("/admin/posts/comments")
    public String postComments(Model model) {
        List<CommentDto> commentDtoList = this.commentService.findAllComments();
        model.addAttribute("commentDtoList", commentDtoList);

        return "admin/comments";
    }

    @PostMapping("/admin/posts")
    public String createPost(@ModelAttribute("postDto") @Valid PostDto postDto, BindingResult bindingResult, Model model) {
        String message = this.messageSource.getMessage("hello", null, Locale.JAPAN);
        System.out.println(message);

        if (bindingResult.hasErrors()) {
            model.addAttribute("postDto", postDto);
            return "admin/new-post";
        }

        postDto.setUrl(getUrl(postDto.getTitle()));
        this.postService.createPost(postDto);

        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = this.postService.findPostById(postId);
        model.addAttribute("postDto", postDto);

        return "admin/edit-post";
    }

    @PostMapping("/admin/posts/{postId}")
    public String editPost(@PathVariable("postId") Long postId,
                           @ModelAttribute("postDto") @Valid PostDto postDto,
                           BindingResult bindingResult,
                           Model model) {

        System.out.println(postDto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("postDto", postDto);
            return "admin/edit-post";
        }

        postDto.setId(postId);
        this.postService.createPost(postDto);

        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        this.postService.deletePost(postId);

        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postUrl}/view")
    public String viewPostForm(@PathVariable("postUrl") String postUrl, Model model) {
        PostDto postDto = this.postService.findPostByUrl(postUrl);
        model.addAttribute("postDto", postDto);

        return "admin/view-post";
    }

    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam("query") String query, Model model) {
        List<PostDto> postDtoList = this.postService.searchPosts(query);
        model.addAttribute("postDtoList", postDtoList);

        return "admin/posts";
    }

    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        this.commentService.deleteComment(commentId);

        return "redirect:/admin/posts/comments";
    }

    private static String getUrl(String title) {
        return title.trim()
                    .toLowerCase()
                    .replace("\\s+", "-")
                    .replaceAll("[^A-Za-z0-9]", "-");
    }
}
