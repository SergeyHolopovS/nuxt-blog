package com.vueblog.utils;

import com.vueblog.post.domain.Post;
import com.vueblog.security.JwtUtils;
import com.vueblog.writer.domain.Writer;
import com.vueblog.writer.repository.WriterRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DBUtils {

    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final WriterRepository writerRepository;

    private static Integer postNumber = 0;
    private static Integer writerNumber = 0;

    public Post createPost(Writer wroteBy) {
        postNumber += 1;
        return Post
                .builder()
                .label(
                    MessageFormat.format("Post {0}", postNumber)
                )
                .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "In maximus euismod massa, consectetur sagittis dui porttitor ac. Vivamus molestie, ante ut porta " +
                    "suscipit, dui dui commodo libero, a tristique mi.")
                .wroteBy(wroteBy)
                .build();
    }

    public Writer createWriter() {
        writerNumber += 1;
        return Writer
                .builder()
                .username(
                    MessageFormat.format("Writer{0}", writerNumber)
                )
                .password(
                    encoder.encode("123")
                )
                .build();
    }

    public void createAdmin() {
        if(writerRepository.findByUsername("Sergey Holopov").isPresent()) return;
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
        writerRepository.save(writer);
    }

    public Cookie adminCookie() {
        Optional<Writer> writer = writerRepository.findByUsername("Sergey Holopov");
        if(writer.isEmpty()) throw new RuntimeException("Admin was not initialized");
        UserDetails userDetails = User
                .builder()
                .username(writer.get().getUsername())
                .password(writer.get().getPassword())
                .authorities(writer.get().getAuthorities())
                .build();
        return new Cookie("authorization", "Bearer_" + jwtUtils.generateGwt(userDetails));
    }

    public Cookie writerCookie(Writer writer) {
        UserDetails userDetails = User
                .builder()
                .username(writer.getUsername())
                .password(writer.getPassword())
                .authorities(writer.getAuthorities())
                .build();
        return new Cookie("authorization", "Bearer_" + jwtUtils.generateGwt(userDetails));
    }

}
