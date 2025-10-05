package com.vueblog.post.web.dto.response;

import com.vueblog.writer.web.dto.response.WriterDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private UUID id;
    private LocalDate createdAt;
    private String label;
    private String text;
    private WriterDTO wroteBy;
}
