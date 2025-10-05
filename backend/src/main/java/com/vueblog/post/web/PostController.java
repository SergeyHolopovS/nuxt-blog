package com.vueblog.post.web;

import com.vueblog.post.domain.Post;
import com.vueblog.post.mapper.PostMapper;
import com.vueblog.post.service.PostService;
import com.vueblog.post.web.dto.request.CreatePostRequest;
import com.vueblog.post.web.dto.request.DeletePostRequest;
import com.vueblog.post.web.dto.response.PostDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService service;
    private final PostMapper mapper;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> posts = service.getAll();
        return ResponseEntity.ok(
                posts
                    .stream()
                    .map(mapper::toDto)
                    .toList()
        );
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody CreatePostRequest request) {
        Post post = service.createPost(mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toDto(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
