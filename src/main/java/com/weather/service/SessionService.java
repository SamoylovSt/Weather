package com.weather.service;

import com.weather.dao.SessionDao;
import com.weather.dao.UserDao;
import com.weather.entity.Session;
import com.weather.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {
    @Autowired
    private SessionDao sessionDao;

    public void createSession(User user, String sessionId) {
        LocalDate ld = (LocalDate.now()).plusDays(1);
        Session session = new Session();
        session.setId(UUID.fromString(sessionId));
        session.setUser(user);
        session.setExpiresAt(ld);
        sessionDao.save(session);
    }

    public Optional<Session> findSession(String sessionId) {
        return sessionDao.findSession(sessionId);
    }

    public boolean existById(String sessionId) {
        return sessionDao.existById(sessionId);
    }

    public boolean isSessionExpired(String sessionId) {
        return sessionDao.isSessionExpired(sessionId);
    }

    public void deleteSession(Session session) {
        sessionDao.deleteSession(session);
    }

}
