package com.devozs.components.harticle.training.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

/**
 * Generates and verifies the two agent secrets — the one-time enrollment code and
 * the per-agent bearer token — and stores only their SHA-256 hashes. Plaintext is
 * returned to the caller exactly once and never persisted.
 */
@Service
public class AgentTokenService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Base64.Encoder URL_B64 = Base64.getUrlEncoder().withoutPadding();

    /** Short, human-typeable enrollment code, e.g. {@code HRT-ab12cd34ef56}. */
    public String generateEnrollmentCode() {
        byte[] bytes = new byte[9];
        RANDOM.nextBytes(bytes);
        return "HRT-" + URL_B64.encodeToString(bytes);
    }

    /** Long opaque bearer token the agent sends on every authenticated call. */
    public String generateAgentToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return URL_B64.encodeToString(bytes);
    }

    public String hash(String secret) {
        if (secret == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(secret.trim().getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }

    public boolean matches(String secret, String expectedHash) {
        if (secret == null || expectedHash == null) {
            return false;
        }
        return MessageDigest.isEqual(
                hash(secret).getBytes(StandardCharsets.UTF_8),
                expectedHash.getBytes(StandardCharsets.UTF_8));
    }
}
