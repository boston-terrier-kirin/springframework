package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {

    private final PostService postService;

    @Autowired
    public BlogController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        List<PostDto> postDtoList = this.postService.findAllPosts();
        model.addAttribute("postDtoList", postDtoList);

        return "blog/view-posts";
    }

    @GetMapping("/post/{postUrl}")
    public String viewPost(@PathVariable("postUrl") String postUrl, Model model) {
        PostDto postDto = this.postService.findPostByUrl(postUrl);
        model.addAttribute("postDto", postDto);
        model.addAttribute("commentDto", new CommentDto());

        return "blog/blog-post";
    }

    @GetMapping("/page/search")
    public String searchPosts(@RequestParam("query") String query, Model model) {
        List<PostDto> postDtoList = this.postService.searchPosts(query);
        model.addAttribute("postDtoList", postDtoList);

        return "blog/view-posts";
    }
}
