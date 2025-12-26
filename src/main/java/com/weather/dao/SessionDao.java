package com.weather.dao;

import com.weather.entity.Session;
import com.weather.exception.PersistException;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class SessionDao {
    @PersistenceContext
    private EntityManager entityManager;

    private final String FIND_SESSION = "SELECT s FROM Session s WHERE s.id=:sessionId";
    private final String FIND_SESSION_IF_EXPIRED = "SELECT s FROM Session s WHERE s.expiresAt < :currentTime";

    @Transactional
    public Session save(Session session) {
        try {
            return entityManager.merge(session);
        } catch (Exception e) {
            throw new PersistException("Session saving error");
        }
    }

    public Optional<Session> findSession(String sessionId) {
        TypedQuery<Session> query = entityManager.createQuery(FIND_SESSION,
                Session.class);
        query.setParameter("sessionId", UUID.fromString(sessionId));
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("multiple sessions found");
        }
    }


    @Transactional
    public void deleteSession(String sessionId) {
        Optional<Session> sessionOpt = findSession(sessionId);
        Session session = sessionOpt.get();
        log.info(session + "  session from BD");
        entityManager.remove(session);
    }

    public boolean isSessionExpired(String sessionId) {
        Optional<Session> sessionOptional = findSession(sessionId);
        if (!sessionOptional.isPresent()) {
            return true;
        }
        Session session = sessionOptional.get();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireLocalDate = session.getExpiresAt();
        return now.isAfter(expireLocalDate);
    }

    public List<Session> findSessionIfExpired() {
        TypedQuery<Session> query = entityManager.createQuery(FIND_SESSION_IF_EXPIRED,
                Session.class);
        query.setParameter("currentTime", LocalDateTime.now());
        return query.getResultList();
    }

    @Transactional
    public void deleteSessionIfExpired() {
        List<Session> sessionsForDelete = findSessionIfExpired();
        if (!sessionsForDelete.isEmpty()) {
            Session sessionForDelete = sessionsForDelete.get(0);
            entityManager.remove(sessionForDelete);
        } else {
            System.out.println("SESSION IS EMPTY");
        }
    }
}
