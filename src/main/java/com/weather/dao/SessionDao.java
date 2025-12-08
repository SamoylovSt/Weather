package com.weather.dao;

import com.weather.entity.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Session session) {
        entityManager.persist(session);
    }

    public Optional<Session> findSession(String sessionId) {
        TypedQuery<Session> query = entityManager.createQuery("SELECT s FROM Session s WHERE s.id=:sessionId",
                Session.class);
        query.setParameter("sessionId", UUID.fromString(sessionId));
        try {
            System.out.println("session found");
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public boolean existById(String sessionId) {
        return findSession(sessionId).isPresent();
    }

    public void deleteSession(Session session) {
        entityManager.remove(session);
    }

    public boolean isSessionExpired(String sessionId) {
        Optional<Session> sessionOptional = findSession(sessionId);
        if (!sessionOptional.isPresent()) {
            return true;
        }
        Session session = sessionOptional.get();
        LocalDate now = LocalDate.now();
        LocalDate expireLocalDate = session.getExpiresAt();
        return now.isAfter(expireLocalDate);
    }
}
