package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller for Post management (CSR 방식)
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listPosts(@RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 5;
        List<PostListDto> posts = postService.getPostsPage(page, pageSize);
        long totalCount = postService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        response.put("currentPage", page);
        response.put("totalPages", totalPages);
        response.put("totalCount", totalCount);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> viewPost(@PathVariable("id") Long id) {
        PostResponseDTO post = postService.getPost(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody PostCreateDto createDto) {
        postService.createPost(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable("id") Long id,
            @Valid @RequestBody PostUpdateDto updateDto) {
        postService.updatePost(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<List<PostTag>> getTags(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getTagsByPostId(id));
    }
}
