package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "게시글 수정 요청 정보")
public record PostUpdateDto(
        @Schema(description = "수정할 제목", example = "수정된 게시글 제목") @NotBlank(message = "제목은 필수입니다.") @Size(max = 100, message = "제목은 100자 이내여야 합니다.") String title,

        @Schema(description = "수정할 내용", example = "수정된 게시글 내용입니다.") @NotBlank(message = "내용은 필수입니다.") String content,

        @Schema(description = "태그 (쉼표로 구분)", example = "java,spring") String tags) {

    public PostUpdateDto() {
        this(null, null, null);
    }

    public static PostUpdateDto from(Post post) {
        return new PostUpdateDto(post.getTitle(), post.getContent(), "");
    }

    public void updateEntity(Post post) {
        post.setTitle(this.title);
        post.setContent(this.content);
    }
}
