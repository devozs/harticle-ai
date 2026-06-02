package com.devozs.components.harticle.training.repository;

import com.devozs.components.harticle.training.entity.TrainingLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingLogRepository extends JpaRepository<TrainingLog, UUID> {

    /** Tail incrementally: only lines after the last seq the FE has seen. */
    List<TrainingLog> findBySessionIdAndSeqGreaterThanOrderBySeqAsc(UUID sessionId, long seq);

    List<TrainingLog> findBySessionIdOrderBySeqAsc(UUID sessionId);

    /** Highest seq so far for a session, to assign the next line's seq. */
    long countBySessionId(UUID sessionId);

    void deleteBySessionId(UUID sessionId);
}
