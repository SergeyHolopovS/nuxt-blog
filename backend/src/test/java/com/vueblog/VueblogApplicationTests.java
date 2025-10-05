package com.vueblog;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vueblog.post.domain.Post;
import com.vueblog.post.repository.PostRepository;
import com.vueblog.post.web.dto.request.CreatePostRequest;
import com.vueblog.post.web.dto.response.PostDTO;
import com.vueblog.security.web.dto.request.LoginRequest;
import com.vueblog.utils.DBUtils;
import com.vueblog.writer.domain.Writer;
import com.vueblog.writer.repository.WriterRepository;
import com.vueblog.writer.web.dto.request.CreateWriterRequest;
import jakarta.servlet.http.Cookie;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(classes = {VueblogApplication.class})
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Import({DBUtils.class, TestcontainersConfiguration.class})
public class VueblogApplicationTests {

    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Autowired
    MockMvc mvc;

    @Autowired
    WriterRepository writerRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    DBUtils utils;

    Writer writer1;
    Writer writer2;
    Writer writer3;

    Post post1;
    Post post2;
    Post post3;

    final static String BASE_URL = "/api/v1/";

    @BeforeEach
    void beforeTest() {
        postRepository.deleteAll();
        writerRepository.deleteAll();

        utils.createAdmin();

        writer1 = writerRepository.save(utils.createWriter());
        writer2 = writerRepository.save(utils.createWriter());
        writer3 = writerRepository.save(utils.createWriter());

        post1 = postRepository.save(utils.createPost(writer1));
        post2 = postRepository.save(utils.createPost(writer2));
        post3 = postRepository.save(utils.createPost(writer3));
    }

    @Test
    @DisplayName("Проверка инициализации")
    void check_init() {
        Assertions.assertEquals(3, postRepository.findAll().size());
        // Так как ещё создаётся admin
        Assertions.assertEquals(4, writerRepository.findAll().size());
    }

    @Nested
    @DisplayName("Тесты постов")
    class posts {

        @Test
        @DisplayName("Получение постов")
        void get_posts() throws Exception {
            String response = mvc
                    .perform(
                            get(BASE_URL + "posts")
                    )
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            JavaType typeRef = objectMapper.getTypeFactory().constructCollectionType(List.class, PostDTO.class);
            List<PostDTO> posts = objectMapper.readValue(response, typeRef);
            Assertions.assertEquals(3, posts.toArray().length);
        }

        @Test
        @DisplayName("Создание поста")
        void create_post() throws Exception {
            CreatePostRequest request = new CreatePostRequest("New post", "Some text here");
            String response = mvc
                .perform(
                    post(BASE_URL + "posts")
                        .cookie(utils.adminCookie())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
            PostDTO post = objectMapper.readValue(response, PostDTO.class);
            Assertions.assertAll(
                () -> Assertions.assertEquals(post.getLabel(), request.label()),
                () -> Assertions.assertEquals(post.getText(), request.text())
            );
        }

        @Test
        @DisplayName("Создание поста (ошибка)")
        void create_post_failure() throws Exception {
            CreatePostRequest request = new CreatePostRequest("", "");
            mvc
                .perform(
                    post(BASE_URL + "posts")
                        .cookie(utils.adminCookie())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Создание поста (нет доступа)")
        void create_post_failure_access() throws Exception {
            CreatePostRequest request = new CreatePostRequest("New post", "Some text here");
            mvc
                .perform(
                    post(BASE_URL + "posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Удаление поста")
        void delete_post() throws Exception {
            mvc
                .perform(
                        delete(BASE_URL + "posts/" + post1.getId())
                            .cookie(utils.adminCookie())
                )
                .andExpect(status().isNoContent());
            Assertions.assertEquals(2, postRepository.findAll().size());
        }

        @Test
        @DisplayName("Удаление поста (ошибка)")
        void delete_post_failure() throws Exception {
            mvc
                .perform(
                    delete(BASE_URL + "posts/" + UUID.randomUUID())
                        .cookie(utils.adminCookie())
                )
                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Удаление поста (нет доступа)")
        void delete_post_failure_access() throws Exception {
            mvc
                .perform(
                    delete(BASE_URL + "posts/" + post1.getId())
                )
                .andExpect(status().isForbidden());
        }

    }

    @Nested
    @DisplayName("Тесты писателей")
    class writers {

        @Test
        @DisplayName("Тест входа")
        void login() throws Exception {

            LoginRequest request = new LoginRequest(writer1.getUsername(), "123");
            Cookie auth = mvc.perform(
                post(BASE_URL + "auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            ).andExpect(
                    status().isNoContent()
            ).andReturn()
            .getResponse()
            .getCookie("authorization");

            Assertions.assertNotNull(auth);
            Assertions.assertTrue(auth.getValue().startsWith("Bearer_"));
        }

        @Test
        @DisplayName("Тест выхода")
        void logout() throws Exception {

            Cookie auth = mvc.perform(
                get(BASE_URL + "auth/logout")
                    .cookie(utils.adminCookie())
                ).andExpect(
                        status().isNoContent()
                ).andReturn()
                .getResponse()
                .getCookie("authorization");

            Assertions.assertNotNull(auth);
            Assertions.assertEquals(0, auth.getMaxAge());

        }

        @Test
        @DisplayName("Тест выхода (без куков)")
        void logout_failure() throws Exception {

            mvc.perform(
                get(BASE_URL + "auth/logout")
            ).andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("Тест создания писателя")
        void create_writer() throws Exception {

            CreateWriterRequest request = new CreateWriterRequest("test writer", "123123123");
            mvc.perform(
                post(BASE_URL + "writers")
                    .cookie(utils.adminCookie())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isNoContent());

        }

        @Test
        @DisplayName("Тест создания писателя (ошибка)")
        void create_writer_failure() throws Exception {

            CreateWriterRequest request = new CreateWriterRequest("test", "123123123");
            mvc.perform(
                post(BASE_URL + "writers")
                    .cookie(utils.adminCookie())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("Тест создания писателя (нет авторизирован)")
        void create_writer_failure_authorization() throws Exception {

            CreateWriterRequest request = new CreateWriterRequest("test writer", "123123123");
            mvc.perform(
                post(BASE_URL + "writers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isForbidden());

        }

        @Test
        @DisplayName("Тест создания писателя (нет доступа)")
        void create_writer_failure_access() throws Exception {

            CreateWriterRequest request = new CreateWriterRequest("test writer", "123123123");
            mvc.perform(
                post(BASE_URL + "writers")
                    .cookie(utils.writerCookie(writer1))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isForbidden());

        }

        @Test
        @DisplayName("Тест удаления писателя")
        void delete_writer() throws Exception {

            mvc.perform(
                delete(BASE_URL + "writers/" + writer1.getId())
                    .cookie(utils.adminCookie())
            )
            .andExpect(status().isNoContent());

        }

        @Test
        @DisplayName("Тест удаления писателя (ошибка)")
        void delete_writer_failure() throws Exception {

            mvc.perform(
                delete(BASE_URL + "writers/" + UUID.randomUUID())
                    .cookie(utils.adminCookie())
            )
            .andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("Тест удаления писателя (нет авторизирован)")
        void delete_writer_failure_authorization() throws Exception {

            mvc.perform(
                delete(BASE_URL + "writers/" + writer1.getId())
            )
            .andExpect(status().isForbidden());

        }

        @Test
        @DisplayName("Тест удаления писателя (нет доступа)")
        void delete_writer_failure_access() throws Exception {

            mvc.perform(
                delete(BASE_URL + "writers/" + writer1.getId())
                    .cookie(utils.writerCookie(writer1))
            )
            .andExpect(status().isForbidden());

        }

    }
}
