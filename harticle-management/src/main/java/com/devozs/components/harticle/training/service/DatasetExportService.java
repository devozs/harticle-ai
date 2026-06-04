package com.devozs.components.harticle.training.service;

import com.devozs.components.harticle.scraper.entity.ScrapedArticle;
import com.devozs.components.harticle.scraper.repository.ScrapedArticleRepository;
import com.devozs.components.harticle.training.entity.TrainingSession;
import com.devozs.components.harticle.training.storage.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Materializes a training session's dataset from the scraped articles into a
 * JSONL object in shared storage, one {@code {title, subTitle, content}} record
 * per line. Reuses {@link ScrapedArticleRepository} (the same table the scraper
 * fills) so training always sees the latest scraped corpus.
 *
 * <p>The agent then pulls this object directly from storage (presigned URL on S3,
 * shared mount or HTTPS endpoint on local-fs), keeping large datasets off the
 * Java heap and out of the request path.
 */
@Service
@Slf4j
public class DatasetExportService {

    private final ScrapedArticleRepository articleRepository;
    private final StorageService storageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DatasetExportService(ScrapedArticleRepository articleRepository,
                                StorageService storageService) {
        this.articleRepository = articleRepository;
        this.storageService = storageService;
    }

    public static String datasetKey(UUID sessionId) {
        return "datasets/" + sessionId + ".jsonl";
    }

    /**
     * Export the session's dataset to {@code datasets/{id}.jsonl} and return its
     * storage URI. {@code reporterIds} (from the session's datasetSpec) scopes the
     * rows; empty/absent means the full corpus.
     */
    public String export(TrainingSession session, List<UUID> reporterIds) {
        String key = datasetKey(session.getId());

        // Build JSONL in memory. GPT-Neo-scale corpora are modest; if this grows,
        // swap to a piped stream writer — the storage API already takes an InputStream.
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        long count = 0;
        Iterable<ScrapedArticle> source = (reporterIds == null || reporterIds.isEmpty())
                ? articleRepository.findAll()
                : reporterIds.stream().flatMap(id -> articleRepository.findByReporterId(id).stream())::iterator;

        try {
            for (ScrapedArticle a : source) {
                if (a.getContent() == null || a.getContent().isBlank()) {
                    continue;
                }
                ObjectNode node = objectMapper.createObjectNode();
                node.put("title", nullSafe(a.getTitle()));
                node.put("subTitle", nullSafe(a.getSubTitle()));
                node.put("content", a.getContent());
                buffer.write(objectMapper.writeValueAsBytes(node));
                buffer.write('\n');
                count++;
            }
        } catch (IOException e) {
            throw new UncheckedIOException("failed to build dataset for session " + session.getId(), e);
        }

        byte[] bytes = buffer.toByteArray();
        String uri = storageService.write(key, new ByteArrayInputStream(bytes), bytes.length);
        log.info("exported {} dataset rows ({} bytes) for session {} -> {}", count, bytes.length, session.getId(), uri);
        return uri;
    }

    /**
     * Copy an already-exported dataset to a new session's key, byte-for-byte, so a
     * re-run trains on the EXACT same data without re-querying the corpus (which
     * could have changed). The dataset endpoint serves per-session keys
     * ({@code datasets/{id}.jsonl}), so a re-run can't just reuse the parent's URI
     * string — it needs its own key populated. Returns the new session's URI, or
     * null if the source dataset doesn't exist (caller should re-export instead).
     */
    public String copyDataset(UUID fromSessionId, UUID toSessionId) {
        String src = datasetKey(fromSessionId);
        if (!storageService.exists(src)) {
            return null;
        }
        String dst = datasetKey(toSessionId);
        try (InputStream in = storageService.read(src);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            in.transferTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String uri = storageService.write(dst, new ByteArrayInputStream(bytes), bytes.length);
            log.info("copied dataset {} -> {} ({} bytes)", src, dst, bytes.length);
            return uri;
        } catch (IOException e) {
            throw new UncheckedIOException("failed to copy dataset " + src + " -> " + dst, e);
        }
    }

    /** Distinct reporter ids referenced (helper kept for callers/tests). */
    public Set<UUID> allReporterIds() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(), false)
                .map(a -> a.getReporter() == null ? null : a.getReporter().getId())
                .filter(java.util.Objects::nonNull)
                .collect(java.util.stream.Collectors.toSet());
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }
}
