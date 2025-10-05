package com.vueblog.writer.service;

import com.vueblog.exceptions.writers.WriterNotFoundException;
import com.vueblog.post.service.PostService;
import com.vueblog.writer.domain.Writer;
import com.vueblog.writer.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WriterService {

    private final PostService postService;
    private final WriterRepository repository;
    private final PasswordEncoder encoder;

    public List<Writer> getAll() { return repository.findAll(); }

    public void createWriter(Writer writer) {
        writer.setPassword(
                encoder.encode(writer.getPassword())
        );
        repository.save(writer);
    }

    public void deleteWriter(UUID id) {
        Optional<Writer> writer = repository.findById(id);
        if(writer.isPresent()) {
            postService.deleteAllPosts(writer.get());
            repository.delete(writer.get());
        }
        else throw new WriterNotFoundException();
    }

    public Optional<Writer> getByUsername(String username) { return repository.findByUsername(username); }

}
