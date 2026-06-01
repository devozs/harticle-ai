package com.devozs.components.harticle.scraper.service;

import com.devozs.components.harticle.scraper.dto.ScrapeProgress;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Thread-safe, in-memory tracker for the current scrape run. The FE polls
 * {@link #snapshot()} to show live progress and detect a stalled backend.
 *
 * <p>Single-run model: a new run resets the counters. This is intentionally
 * simple (no history/persistence) - it exists to answer "is it moving or
 * stuck?", which is all the admin UI needs. The {@code lastActivity} timestamp
 * is bumped on every page/article touch; if it stops advancing while
 * {@link #running} is true, the run is wedged.
 */
@Component
public class ScrapeProgressTracker {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean cancelRequested = new AtomicBoolean(false);
    private final AtomicReference<String> phase = new AtomicReference<>("idle");
    private final AtomicReference<String> currentSite = new AtomicReference<>("");
    private final AtomicReference<String> currentReporter = new AtomicReference<>("");
    private final AtomicReference<String> currentUrl = new AtomicReference<>("");
    private final AtomicReference<String> lastMessage = new AtomicReference<>("");
    private final AtomicInteger currentPage = new AtomicInteger(0);

    private final AtomicInteger reportersProcessed = new AtomicInteger(0);
    private final AtomicInteger pagesFetched = new AtomicInteger(0);
    private final AtomicInteger articlesSaved = new AtomicInteger(0);
    private final AtomicInteger articlesUpdated = new AtomicInteger(0);
    private final AtomicInteger articlesSkipped = new AtomicInteger(0);
    private final AtomicInteger errors = new AtomicInteger(0);

    private final AtomicLong startedAt = new AtomicLong(0);
    private final AtomicLong lastActivity = new AtomicLong(0);
    private final AtomicLong finishedAt = new AtomicLong(0);

    /** Begin a new run, resetting all counters. Label is a human description. */
    public void start(String label) {
        running.set(true);
        cancelRequested.set(false);
        phase.set("starting");
        currentSite.set("");
        currentReporter.set("");
        currentUrl.set("");
        currentPage.set(0);
        reportersProcessed.set(0);
        pagesFetched.set(0);
        articlesSaved.set(0);
        articlesUpdated.set(0);
        articlesSkipped.set(0);
        errors.set(0);
        long now = System.currentTimeMillis();
        startedAt.set(now);
        finishedAt.set(0);
        lastActivity.set(now);
        lastMessage.set(label == null ? "" : label);
    }

    public void finish() {
        boolean wasCancelled = cancelRequested.get();
        running.set(false);
        cancelRequested.set(false);
        phase.set(wasCancelled ? "cancelled" : "done");
        long now = System.currentTimeMillis();
        finishedAt.set(now);
        lastActivity.set(now);
        if (wasCancelled) {
            lastMessage.set("run stopped by user");
        }
    }

    /**
     * Request the in-flight run to stop. The running thread checks
     * {@link #isCancelRequested()} at loop boundaries and bails out promptly.
     * No-op if nothing is running.
     *
     * @return true if a run was active and a stop was requested
     */
    public boolean requestCancel() {
        if (!running.get()) {
            return false;
        }
        cancelRequested.set(true);
        phase.set("stopping");
        lastMessage.set("stop requested");
        return true;
    }

    public boolean isCancelRequested() {
        return cancelRequested.get();
    }

    public void beginReporter(String site, String reporter) {
        currentSite.set(site == null ? "" : site);
        currentReporter.set(reporter == null ? "" : reporter);
        reportersProcessed.incrementAndGet();
        touch("reporter " + reporter);
    }

    public void beginListing(String url, int page) {
        phase.set("listing");
        currentUrl.set(url == null ? "" : url);
        currentPage.set(page);
        touch(null);
    }

    public void pageFetched() {
        pagesFetched.incrementAndGet();
        touch(null);
    }

    public void beginArticle(String url) {
        phase.set("article");
        currentUrl.set(url == null ? "" : url);
        touch(null);
    }

    public void saved() {
        articlesSaved.incrementAndGet();
        touch(null);
    }

    public void updated() {
        articlesUpdated.incrementAndGet();
        touch(null);
    }

    public void skipped() {
        articlesSkipped.incrementAndGet();
        touch(null);
    }

    public void error(String message) {
        errors.incrementAndGet();
        touch(message);
    }

    /** Bump the activity timestamp (and optionally a message); the heartbeat. */
    private void touch(String message) {
        lastActivity.set(System.currentTimeMillis());
        if (message != null) {
            lastMessage.set(message);
        }
    }

    public boolean isRunning() {
        return running.get();
    }

    public ScrapeProgress snapshot() {
        long last = lastActivity.get();
        Long sinceSeconds = last == 0 ? null : (System.currentTimeMillis() - last) / 1000;
        return ScrapeProgress.builder()
                .running(running.get())
                .cancelRequested(cancelRequested.get())
                .phase(phase.get())
                .currentSite(currentSite.get())
                .currentReporter(currentReporter.get())
                .currentUrl(currentUrl.get())
                .currentPage(currentPage.get())
                .reportersProcessed(reportersProcessed.get())
                .pagesFetched(pagesFetched.get())
                .articlesSaved(articlesSaved.get())
                .articlesUpdated(articlesUpdated.get())
                .articlesSkipped(articlesSkipped.get())
                .errors(errors.get())
                .startedAtEpochMs(startedAt.get() == 0 ? null : startedAt.get())
                .lastActivityEpochMs(last == 0 ? null : last)
                .finishedAtEpochMs(finishedAt.get() == 0 ? null : finishedAt.get())
                .secondsSinceActivity(sinceSeconds)
                .lastMessage(lastMessage.get())
                .build();
    }
}
