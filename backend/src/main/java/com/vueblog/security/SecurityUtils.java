package com.vueblog.security;

import com.vueblog.writer.domain.Writer;
import com.vueblog.writer.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final WriterRepository repository;

    public Optional<Writer> getWriter() {
        return repository
            .findByUsername(
                SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName()
            );
    }

}
