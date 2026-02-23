package com.example.vibeapp.controller;

import com.example.vibeapp.model.Post;
import com.example.vibeapp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/posts/{no}")
    public String viewPost(@PathVariable("no") Long no, Model model) {
        Post post = postService.getPost(no);
        if (post == null) {
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "post_detail";
    }
}
