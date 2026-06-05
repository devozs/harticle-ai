package com.devozs.components.harticle.inference.repository;

import com.devozs.components.harticle.inference.entity.InferenceRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InferenceRunRepository extends JpaRepository<InferenceRun, UUID> {

    List<InferenceRun> findAllByOrderByCreatedAtDesc();

    /**
     * Atomically pick the next claimable GPU/HPU inference run for a resource of
     * {@code type}. LOCAL runs are excluded ({@code local = false}) — they execute
     * on the deployment CPU and are never claimed by an agent. {@code FOR UPDATE
     * SKIP LOCKED} mirrors the training claim so concurrent agents never grab the
     * same row. Oldest-first for fairness.
     */
    @Query(value = """
            select * from inference_run
             where status = 'PENDING'
               and local = false
               and required_type = :type
             order by created_at
             limit 1
             for update skip locked
            """, nativeQuery = true)
    Optional<InferenceRun> lockNextClaimable(@Param("type") String type);
}
