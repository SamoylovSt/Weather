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

    public void save(Location location,int userId) {
        locationDao.save(location,userId);
    }

    public List<Location> getLocationsForCurrentUser(int userId) {
        return locationDao.getLocationsForCurrentUser(userId);
    }

    public List<WeatherDTO> getLocationListForIndex(List<Location> locations) {
        List<WeatherDTO> resultList = new ArrayList<>();
        for (Location location : locations) {
            WeatherDTO temp = openWeatherMapService.getWeatherByCoordinates(location.getLatitude().doubleValue(),
                    location.getLongitude().doubleValue());
            temp.setCity(location.getName());
            resultList.add(temp);
        }
        return resultList;
    }

    public void deleteLocation(String city, int currentUserId) {
        locationDao.deleteLocation(city, currentUserId);
    }

}
