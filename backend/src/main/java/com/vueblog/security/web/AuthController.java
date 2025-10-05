package com.vueblog.security.web;

import com.vueblog.exceptions.cookies.NoCookieException;
import com.vueblog.security.SecurityUtils;
import com.vueblog.security.service.AuthService;
import com.vueblog.security.web.dto.request.LoginRequest;
import com.vueblog.writer.domain.Writer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final SecurityUtils utils;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody LoginRequest data
    ) {
        String token = "Bearer_" + service.login(data);
        Cookie cookie = new Cookie("authorization", token);
        cookie.setPath("/api");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0) throw new NoCookieException();
        Arrays.stream(cookies).forEach(el -> {
            el.setValue("");
            el.setMaxAge(0);
            response.addCookie(el);
        });
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> check() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-admin")
    public ResponseEntity<Boolean> checkAdmin() {
        Optional<Writer> writer = utils.getWriter();
        if (writer.isPresent()) {
            log.info("{} {}", writer.get().getRoles(), writer.get().getAuthorities());
            return ResponseEntity.ok(
                    writer
                        .get()
                        .getRoles()
                        .contains("ROLE_ADMIN")
            );
        }
        return ResponseEntity.ok(false);
    }

}
