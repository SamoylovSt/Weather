package com.weather.dao;

import com.weather.entity.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationDao {
    @PersistenceContext
    private EntityManager entityManager;

    private final String SELECT_LOCATIONS_FOR_CURRENT_USER = "SELECT l FROM Location l WHERE l.user.id =:userId";
    private final String FIND_LOCATION_BY_CITY = "SELECT l FROM Location l WHERE l.name LIKE '%' || :name || '%'";
    private final String FIND_LOCATION_BY_CITY_AND_USER_ID = "SELECT l FROM Location l WHERE l.name LIKE '%' || :name || '%' AND l.user.id=:userId";
    private final String SELECT_LOCATION_BY_LATITUDE_AND_USER_ID="SELECT l FROM Location l WHERE l.latitude =:latitude AND l.user.id=:userId";

    @Transactional
    public void save(Location location, int userId) {
        if (!existLocationByLatitudeAndUserId(location.getLatitude().doubleValue(), userId)) {
            entityManager.persist(location);
        }
    }

    public Location findLocationByCity(String name) {
        TypedQuery<Location> query = entityManager.createQuery(
                FIND_LOCATION_BY_CITY,
                Location.class
        );
        query.setParameter("name", name);
        List<Location> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public Location findLocationByCityAndUserId(String name, int userId) {
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
    }

    public boolean existLocationByLatitudeAndUserId(double latitude, int userId) {
        TypedQuery<Location> query = entityManager.createQuery(
                SELECT_LOCATION_BY_LATITUDE_AND_USER_ID,
                Location.class
        );
        query.setParameter("latitude", latitude);
        query.setParameter("userId", userId);
        try {
            return Optional.of(query.getSingleResult()).isPresent();
        } catch (NoResultException e) {

            return false;
        }

    }

    @Transactional
    public List<Location> getLocationsForCurrentUser(int userId) {
        TypedQuery<Location> query = entityManager.createQuery(SELECT_LOCATIONS_FOR_CURRENT_USER, Location.class);
        query.setParameter("userId", userId);
        List<Location> result = query.getResultList();
        return result;
    }

    @Transactional
    public void deleteLocation(String city, int currentUserId) {
        Location locationForDelete = findLocationByCityAndUserId(city, currentUserId);
        if (locationForDelete != null) {
            entityManager.remove(locationForDelete);
        }
    }
}
