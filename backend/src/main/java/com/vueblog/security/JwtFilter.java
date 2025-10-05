package com.vueblog.security;

import com.vueblog.writer.domain.Writer;
import com.vueblog.writer.service.WriterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils utils;
    private final WriterService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            log.info("Cookies not found");
            filterChain.doFilter(request, response);
            return;
        }

        // Получение кукиса авторизации
        Optional<Cookie> authorization = Arrays.stream(
            cookies
        )
            .filter(el ->
                el.getName().equals("authorization")
            )
            .findFirst();

        // Если нет кука или он невалидный - пропускаем без авторизации
        if(authorization.isEmpty() || !authorization.get().getValue().startsWith("Bearer_")) {
            log.info("Cookie is invalid");
            filterChain.doFilter(request, response);
            return;
        }

        // Получаем токен
        String token = authorization
                            .get()
                            .getValue()
                            .substring(7);

        // Получаем данные пользователя из токена
        String username = null;
        try {
            username = utils.getUsername(token);
        } catch (Exception exception) {
            log.warn("Filter: Error while working with JWT\n{}", exception.getMessage());
        }

        /*
          Проверка данных из БД каждый запрос обусловлено малым количеством
          запросов от авторизированных пользователей, поэтому можно позволить
          себе такой подход для безопасности.
         */
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("Username found and context is empty");
            Optional<Writer> writer = service.getByUsername(username);
            if(writer.isPresent()) {
                log.info("Writer found, setting context");
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            writer
                                .get()
                                .getAuthorities()
                        )
                    );
            }
        }

        log.info("End of filter");
        filterChain.doFilter(request, response);
    }
}
