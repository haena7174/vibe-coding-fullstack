package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;

public record PostResponseDTO(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer views) {
    public static PostResponseDTO from(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getViews());
    }
}
