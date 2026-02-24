package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "게시글 상세 응답 정보")
public record PostResponseDTO(
        @Schema(description = "게시글 ID", example = "1") Long id,
        @Schema(description = "제목", example = "게시글 제목") String title,
        @Schema(description = "내용", example = "게시글 내용...") String content,
        @Schema(description = "생성일") LocalDateTime createdAt,
        @Schema(description = "수정일") LocalDateTime updatedAt,
        @Schema(description = "조회수", example = "10") Integer views) {
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
