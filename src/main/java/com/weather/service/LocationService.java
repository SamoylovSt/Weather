package com.weather.service;

import com.weather.dao.LocationDao;
import com.weather.dto.WeatherDTO;
import com.weather.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    public void save(Location location) {
        locationDao.save(location);
    }

    public List<Location> getLocationsForCurrentUser(int userId) {
        return locationDao.getLocationsForCurrentUser(userId);
    }

    public List<WeatherDTO> getLocationListForIndex(List<Location> locations) {
        List<WeatherDTO> resultList = new ArrayList<>();
        for (Location location : locations) {
            WeatherDTO temp = openWeatherMapService.getWeatherByCoordinates(location.getLatitude().doubleValue(),
                    location.getLongitude().doubleValue());
            resultList.add(temp);
        }
        return resultList;
    }

    public Location findLocationByCity(String city) {
        return locationDao.findLocationByCity(city);
    }

    public void deleteLocation(String city) {
        locationDao.deleteLocation(city);
    }

}
