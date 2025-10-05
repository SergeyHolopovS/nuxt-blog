package com.vueblog.post.service;

import com.vueblog.exceptions.posts.PostNotFoundException;
import com.vueblog.exceptions.writers.WriterNotFoundException;
import com.vueblog.post.domain.Post;
import com.vueblog.post.repository.PostRepository;
import com.vueblog.security.SecurityUtils;
import com.vueblog.writer.domain.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final CacheManager cacheManager;
    private final SecurityUtils securityUtils;
    private final PostRepository repository;

    public List<Post> getAll() { return repository.findAll(); }

    public Post createPost(Post post) {
        Optional<Writer> writer = securityUtils.getWriter();
        if(writer.isEmpty()) throw new WriterNotFoundException();
        post.setWroteBy(writer.get());
        Post createdPost = repository.save(post);
        evictCache();
        return createdPost;
    }

    @Transactional
    public void deletePost(UUID id) {
        Optional<Post> post = repository.findById(id);
        if(post.isPresent()) {
            repository.delete(post.get());
            evictCache();
        } else throw new PostNotFoundException();
    }

    @Transactional
    public void deleteAllPosts(Writer writer) {
        repository.deleteByWroteBy(writer);
    }

    private void evictCache() {
        Cache cache = cacheManager.getCache("posts");
        if(cache != null) {
            cache.evict("posts_all");
        }
    }

}
