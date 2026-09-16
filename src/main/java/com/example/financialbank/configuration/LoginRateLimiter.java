package com.example.financialbank.configuration;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

// Limitador simples em memória contra força bruta no login. Suficiente pra uma
// instância só; se o app rodar em mais de uma instância, isso precisa virar
// um contador compartilhado (Redis) pra funcionar de verdade.
@Component
public class LoginRateLimiter {

    private static final int MAX_ATTEMPTS = 5;
    private static final long WINDOW_SECONDS = 15 * 60;

    private final ConcurrentHashMap<String, Deque<Instant>> attemptsByKey = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        cleanup(key);
        Deque<Instant> attempts = attemptsByKey.get(key);
        return attempts != null && attempts.size() >= MAX_ATTEMPTS;
    }

    public void registerFailedAttempt(String key) {
        cleanup(key);
        attemptsByKey.computeIfAbsent(key, k -> new ConcurrentLinkedDeque<>()).add(Instant.now());
    }

    public void clear(String key) {
        attemptsByKey.remove(key);
    }

    private void cleanup(String key) {
        Deque<Instant> attempts = attemptsByKey.get(key);
        if (attempts == null) return;
        Instant threshold = Instant.now().minusSeconds(WINDOW_SECONDS);
        while (!attempts.isEmpty() && attempts.peekFirst() != null && attempts.peekFirst().isBefore(threshold)) {
            attempts.pollFirst();
        }
    }
}
