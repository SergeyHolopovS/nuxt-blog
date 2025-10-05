package com.vueblog.post.repository;

import com.vueblog.post.domain.Post;
import com.vueblog.writer.domain.Writer;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@CacheConfig(cacheNames = "posts")
public interface PostRepository extends JpaRepository<Post, UUID> {
    @Override
    @Cacheable(key = "'posts_all'")
    List<Post> findAll();

    @CacheEvict(key = "'posts_all'", allEntries = true)
    void deleteByWroteBy(Writer writer);

}
