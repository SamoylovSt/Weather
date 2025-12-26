package com.weather.dao;

import com.weather.entity.Location;
import com.weather.exception.PersistException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public void save(Location location, long userId) {
           try {
               entityManager.persist(location);
           }catch (RuntimeException e){
             throw new PersistException("Location saving error");
           }
    }

    public Location findLocationByCityAndUserId(String name, long userId) {
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
        if (locationForDelete != null) {
            entityManager.remove(locationForDelete);
        }
    }
}
