package com.weather.service;

import com.weather.dao.SessionDao;
import com.weather.entity.Session;
import com.weather.entity.User;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionService {
    @Autowired
    private SessionDao sessionDao;

    public Session createSession(User user) {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        Session session = new Session();
        session.setUser(user);
        session.setExpiresAt(localDateTime);
        return sessionDao.save(session);
    }

    public Optional<Session> findSession(String sessionId) {
        return sessionDao.findSession(sessionId);
    }

    public boolean isSessionExpired(String sessionId) {
        return sessionDao.isSessionExpired(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessionDao.deleteSession(sessionId);
    }

    public Cookie createCookies(Session session) {
        Cookie newCookie = new Cookie("session", session.getId().toString());
        newCookie.setPath("/");
        newCookie.setMaxAge(24 * 60 * 60);
        return newCookie;
    }

}
