package com.vueblog.writer.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateWriterRequest(
        @NotBlank
        @Size(min = 8, max = 31)
        String username,
        @NotBlank
        @Size(min = 8, max = 31)
        String password
) {
}
