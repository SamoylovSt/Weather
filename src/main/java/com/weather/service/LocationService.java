package com.weather.service;

import com.weather.dao.LocationDao;
import com.weather.dto.WeatherDTO;
import com.weather.entity.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LocationService {

    private final LocationDao locationDao;
    private final OpenWeatherMapService openWeatherMapService;

    public LocationService(OpenWeatherMapService openWeatherMapService, LocationDao locationDao) {
        this.openWeatherMapService = openWeatherMapService;
        this.locationDao = locationDao;
    }

    public void save(Location location) {
        log.info("save location:{}", location);
        locationDao.save(location);
    }

    public List<Location> getLocationsForCurrentUser(long userId) {
        log.info("get location for current userId: {}", userId);
        return locationDao.getLocationsForCurrentUser(userId);
    }

    public List<WeatherDTO> getLocationListForIndex(List<Location> locations) {
        log.info("get location for current userId");
        List<WeatherDTO> resultList = new ArrayList<>();
        for (Location location : locations) {
            WeatherDTO temp = openWeatherMapService.getWeatherByCoordinates(location.getLatitude().doubleValue(),
                    location.getLongitude().doubleValue());
            temp.setCity(location.getName());
            resultList.add(temp);
        }
        return resultList;
    }

    public void deleteLocation(String city, long currentUserId) {
        log.info("delete location for city and city: {}, currentUserId: {}", city, currentUserId);
        locationDao.deleteLocation(city, currentUserId);
    }

}
