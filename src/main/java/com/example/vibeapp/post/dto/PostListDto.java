package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "게시글 목록 응답 정보")
public record PostListDto(
        @Schema(description = "게시글 ID", example = "1") Long id,
        @Schema(description = "제목", example = "게시글 제목") String title,
        @Schema(description = "생성일") LocalDateTime createdAt,
        @Schema(description = "조회수", example = "10") Integer views) {
    public static PostListDto from(Post post) {
        return new PostListDto(
                post.getId(),
                post.getTitle(),
                post.getCreatedAt(),
                post.getViews());
    }
}
