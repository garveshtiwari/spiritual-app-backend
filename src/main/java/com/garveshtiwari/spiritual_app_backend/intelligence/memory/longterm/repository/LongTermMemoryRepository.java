package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.repository;

import com.garveshtiwari.spiritual_app_backend.intelligence.memory.common.MemoryCategory;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryStatus;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.entity.LongTermMemory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LongTermMemoryRepository
        extends JpaRepository<LongTermMemory, Long> {

    List<LongTermMemory> findByUserIdOrderByUpdatedAtDesc(
            Long userId
    );

    List<LongTermMemory> findByUserIdAndStatusOrderByUpdatedAtDesc(
            Long userId,
            MemoryStatus status
    );

    List<LongTermMemory> findByUserIdAndStatus(
            Long userId,
            MemoryStatus status
    );

    Optional<LongTermMemory> findByIdAndUserId(
            Long id,
            Long userId
    );

    Optional<LongTermMemory> findByUserIdAndCategoryAndContentIgnoreCaseAndStatus(
            Long userId,
            MemoryCategory category,
            String content,
            MemoryStatus status
    );
}