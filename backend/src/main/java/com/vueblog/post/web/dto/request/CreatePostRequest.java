package com.vueblog.post.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreatePostRequest(
        @NotBlank
        String label,
        @NotBlank
        String text
) {
}
