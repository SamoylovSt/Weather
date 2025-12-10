package com.weather.dao;

import com.weather.entity.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LocationDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Location location) {
        entityManager.persist(location);
    }

    @Transactional
    public List<Location> getLocationsForCurrentUser(int userId) {
        TypedQuery<Location> query = entityManager.createQuery("SELECT l FROM Location l WHERE l.user.id =:userId", Location.class);
        query.setParameter("userId", userId);
        List<Location> result = query.getResultList();
        return result;
    }
}
