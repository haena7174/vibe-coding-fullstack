package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "게시글 생성 요청 정보")
public record PostCreateDto(
        @Schema(description = "게시글 제목", example = "새로운 게시글 제목") @NotBlank(message = "제목은 필수입니다.") @Size(max = 100, message = "제목은 100자 이내여야 합니다.") String title,

        @Schema(description = "게시글 내용", example = "게시글의 상세 내용입니다.") @NotBlank(message = "내용은 필수입니다.") String content,

        @Schema(description = "태그 (쉼표로 구분)", example = "java,spring,swagger") String tags) {

    public PostCreateDto() {
        this(null, null, null);
    }

    public Post toEntity() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setCreatedAt(LocalDateTime.now());
        post.setViews(0);
        return post;
    }
}
