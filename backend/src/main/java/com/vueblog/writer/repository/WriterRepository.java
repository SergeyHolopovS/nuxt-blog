package com.vueblog.writer.repository;

import com.vueblog.writer.domain.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WriterRepository extends JpaRepository<Writer, UUID> {
    @Override
    List<Writer> findAll();

    Optional<Writer> findById(UUID id);

    Optional<Writer> findByUsername(String username);
}
