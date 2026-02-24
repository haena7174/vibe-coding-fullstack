package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 5;
        List<PostListDto> posts = postService.getPostsPage(page, pageSize);
        long totalCount = postService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "post/posts";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model) {
        PostResponseDTO post = postService.getPost(id);
        if (post == null) {
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        model.addAttribute("tags", postService.getTagsByPostId(id));
        return "post/post_detail";
    }

    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        model.addAttribute("postCreateDto", new PostCreateDto());
        return "post/post_new_form";
    }

    @PostMapping("/posts/add")
    public String createPost(@Valid @ModelAttribute("postCreateDto") PostCreateDto createDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/post_new_form";
        }
        postService.createPost(createDto);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPostForm(@PathVariable("id") Long id, Model model) {
        PostUpdateDto updateDto = postService.getPostForEdit(id);
        if (updateDto == null) {
            return "redirect:/posts";
        }
        model.addAttribute("postUpdateDto", updateDto);
        model.addAttribute("postId", id);
        return "post/post_edit_form";
    }

    @PostMapping("/posts/{id}/save")
    public String updatePost(@PathVariable("id") Long id,
            @Valid @ModelAttribute("postUpdateDto") PostUpdateDto updateDto,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postId", id);
            return "post/post_edit_form";
        }
        postService.updatePost(id, updateDto);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
