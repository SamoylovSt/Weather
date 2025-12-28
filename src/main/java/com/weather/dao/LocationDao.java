package com.weather.dao;

import com.weather.entity.Location;
import com.weather.exception.AppException;
import com.weather.exception.NotFoundException;
import com.weather.exception.PersistException;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class LocationDao {
    @PersistenceContext
    private EntityManager entityManager;

    private final String SELECT_LOCATIONS_FOR_CURRENT_USER = "SELECT l FROM Location l WHERE l.user.id =:userId";
    private final String FIND_LOCATION_BY_CITY_AND_USER_ID = "SELECT l FROM Location l WHERE l.name LIKE '%' || :name || '%' AND l.user.id=:userId";

    @Transactional
    public void save(Location location) {
        try {
            entityManager.persist(location);
        } catch (PersistenceException e) {
            log.error("Failed to persist location: {}", location.getName(), e);
            throw new PersistException(" location", e);
        }
    }

    public Location findLocationByCityAndUserId(String name, long userId) {

        try {
            TypedQuery<Location> query = entityManager.createQuery(FIND_LOCATION_BY_CITY_AND_USER_ID,
                    Location.class
            );
            query.setParameter("name", name);
            query.setParameter("userId", userId);
            List<Location> results = query.getResultList();
            if (results.isEmpty()) {
                return null;
            }
            return results.get(0);
        } catch (AppException e) {
            log.error("Failed to find location: {}, userId: {}", name, userId, e);
            throw new NotFoundException("Location + userId ", name + userId);
        }
    }

    @Transactional
    public List<Location> getLocationsForCurrentUser(long userId) {
        TypedQuery<Location> query = entityManager.createQuery(SELECT_LOCATIONS_FOR_CURRENT_USER, Location.class);
        query.setParameter("userId", userId);
        List<Location> result = query.getResultList();
        return result;
    }

    @Transactional
    public void deleteLocation(String city, long currentUserId) {
        Location locationForDelete = findLocationByCityAndUserId(city, currentUserId);
        try {
            if (locationForDelete != null) {
                entityManager.remove(locationForDelete);
            }
        } catch (Exception e) {
            throw new NotFoundException("Location", city);
        }

    }
}
