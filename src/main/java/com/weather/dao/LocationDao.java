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

    private final String SELECT_LOCATIONS_FOR_CURRENT_USER = "SELECT l FROM Location l WHERE l.user.id =:userId";
    private final String FIND_LOCATION_BY_CITY = "SELECT l FROM Location l WHERE l.name=:name";

    @Transactional
    public void save(Location location) {
        entityManager.persist(location);
    }

    //    public Location findLocationByCity(String name) {
//        TypedQuery<Location> query = entityManager.createQuery("SELECT l FROM Location l WHERE l.name=:name", Location.class);
//        query.setParameter("name", name);
//        Location result = query.getSingleResult();
//        return result;
//    }
    public Location findLocationByCity(String name) {
        TypedQuery<Location> query = entityManager.createQuery(
                "SELECT l FROM Location l WHERE l.name LIKE '%' || :name || '%'",
                Location.class
        );
        query.setParameter("name", name);

        List<Location> results = query.getResultList();

        if (results.isEmpty()) {
            return null; // или throw new NotFoundException("Location not found");
        }

        return results.get(0); // Возвращаем первую найденную локацию
    }

    @Transactional
    public List<Location> getLocationsForCurrentUser(int userId) {
        TypedQuery<Location> query = entityManager.createQuery(SELECT_LOCATIONS_FOR_CURRENT_USER, Location.class);
        query.setParameter("userId", userId);
        List<Location> result = query.getResultList();
        return result;
    }

    @Transactional
    public void deleteLocation(String city) {
        Location locationForDelete = findLocationByCity(city);
        if (locationForDelete != null) {
            entityManager.remove(locationForDelete);
        }
    }
}
