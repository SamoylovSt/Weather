package com.weather.util;

import com.weather.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SessionCleanupScheduler {
    @Autowired
    private SessionService sessionService;

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void cleanupExpiredSessions() {
        sessionService.deleteSessionIfExpired();
    }
}
