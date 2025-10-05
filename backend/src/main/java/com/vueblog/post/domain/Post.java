package com.vueblog.post.domain;

import com.vueblog.writer.domain.Writer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDate createdAt = LocalDate.now();

    @NotBlank
    @Column(name = "label", length = 63)
    private String label;

    @NotBlank
    @Column(name = "text", length = 1023)
    private String text;

    @ManyToOne
    @JoinColumn(name = "wrote_by")
    private Writer wroteBy;
}
