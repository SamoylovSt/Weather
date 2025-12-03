package com.weather.dao;

import com.weather.entity.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SessionDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Session session) {
        entityManager.persist(session);
    }
}
