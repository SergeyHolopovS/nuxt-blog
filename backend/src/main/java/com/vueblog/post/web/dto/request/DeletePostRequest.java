package com.vueblog.post.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DeletePostRequest(
        @NotNull
        UUID id
) {
}
