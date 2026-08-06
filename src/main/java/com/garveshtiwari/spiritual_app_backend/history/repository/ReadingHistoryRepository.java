package com.garveshtiwari.spiritual_app_backend.history.repository;

import com.garveshtiwari.spiritual_app_backend.history.entity.ReadingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingHistoryRepository
        extends JpaRepository<ReadingHistory, Long> {

    List<ReadingHistory>
    findByUserIdOrderByOpenedAtDesc(
            Long userId
    );
}