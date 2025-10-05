package com.vueblog.security.service;

import com.vueblog.security.JwtUtils;
import com.vueblog.security.web.dto.request.LoginRequest;
import com.vueblog.writer.domain.Writer;
import com.vueblog.writer.service.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder encoder;
    private final WriterService service;
    private final JwtUtils utils;

    public String login(LoginRequest request) {
        Optional<Writer> writer = service.getByUsername(request.username());
        if(writer.isEmpty() || !encoder.matches(request.password(), writer.get().getPassword())) {
            throw new BadCredentialsException("Login and/or password is/are invalid");
        }
        return utils.generateGwt(
            createUserDetails(writer.get())
        );
    }

    private UserDetails createUserDetails(Writer writer) {
        return User.builder()
                .username(writer.getUsername())
                .password(writer.getPassword())
                .build();
    }

}
