package com.example.vibeapp.config;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "API 공통 에러 응답")
public record ErrorResponse(
        @Schema(description = "HTTP 상태 코드", example = "400") int status,

        @Schema(description = "에러 메시지", example = "잘못된 요청입니다.") String message,

        @Schema(description = "발생 시간") LocalDateTime timestamp,

        @Schema(description = "상세 에러 내용 (필드별 검증 오류 등)") Map<String, String> errors) {
    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message, LocalDateTime.now(), null);
    }

    public static ErrorResponse of(int status, String message, Map<String, String> errors) {
        return new ErrorResponse(status, message, LocalDateTime.now(), errors);
    }
}
