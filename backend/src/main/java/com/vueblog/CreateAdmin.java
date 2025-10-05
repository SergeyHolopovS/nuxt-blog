package com.vueblog;

import com.vueblog.writer.domain.Writer;
import com.vueblog.writer.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAdmin implements CommandLineRunner {

    private final WriterRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if(repository.findByUsername("Sergey Holopov").isPresent()) return;
        Writer writer = Writer
                            .builder()
                            .username("Sergey Holopov")
                            .password(
                                encoder.encode("123")
                            )
                            .roles(
                                List.of(
                                    "ROLE_ADMIN"
                                )
                            ).build();
        repository.save(writer);
        log.info("Admin created");
    }

}
