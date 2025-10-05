package com.vueblog.writer.web;

import com.vueblog.writer.mapper.WriterMapper;
import com.vueblog.writer.service.WriterService;
import com.vueblog.writer.web.dto.request.CreateWriterRequest;
import com.vueblog.writer.web.dto.response.WriterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/writers")
@RequiredArgsConstructor
public class WriterController {

    private final WriterService service;
    private final WriterMapper mapper;

    @GetMapping
    public ResponseEntity<List<WriterDTO>> getAllWriters() {
        return ResponseEntity.ok(
            service
                .getAll()
                .stream()
                .map(mapper::toDTO)
                .toList()
        );
    }

    @PostMapping
    public ResponseEntity<?> createWriter(@Valid @RequestBody CreateWriterRequest request) {
        service.createWriter(
                mapper.toEntity(request)
        );
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWriter(@PathVariable UUID id) {
        service.deleteWriter(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
