package com.devozs.components.harticle.training.repository;

import com.devozs.components.harticle.training.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, UUID> {

    List<TrainingSession> findAllByOrderByCreatedAtDesc();

    /**
     * Atomically pick the next claimable session for a resource of {@code type}.
     * {@code FOR UPDATE SKIP LOCKED} guarantees two agents racing to claim never
     * grab the same row: each transaction locks a distinct row and skips any the
     * other already holds. Returns the row; the caller flips it to ASSIGNED.
     *
     * <p>Oldest-first (created_at) so the queue is fair.
     */
    @Query(value = """
            select * from training_session
             where status in ('PENDING', 'RESUMING')
               and required_type = :type
             order by created_at
             limit 1
             for update skip locked
            """, nativeQuery = true)
    Optional<TrainingSession> lockNextClaimable(@Param("type") String type);

    /** RUNNING sessions whose agent has gone quiet past the cutoff (stall reaper). */
    @Query(value = """
            select * from training_session
             where status = 'RUNNING'
               and (last_agent_seen_at is null or last_agent_seen_at < :cutoff)
            """, nativeQuery = true)
    List<TrainingSession> findStalledRunning(@Param("cutoff") java.util.Date cutoff);

    /**
     * Highest {@code attempt_number} across a re-run chain — the original (whose id
     * is {@code root}) plus everything that points at it via parent_session_id.
     * Used to assign the next attempt number on re-run. The root always has
     * attempt 1, so coalescing a no-children result to 1 is correct.
     */
    @Query(value = """
            select coalesce(max(attempt_number), 1) from training_session
             where id = :root or parent_session_id = :root
            """, nativeQuery = true)
    int maxAttemptInChain(@Param("root") UUID root);

    /** All sessions in one re-run chain (the root + its re-runs), oldest attempt first. */
    @Query(value = """
            select * from training_session
             where id = :root or parent_session_id = :root
             order by attempt_number
            """, nativeQuery = true)
    List<TrainingSession> findChain(@Param("root") UUID root);
}
