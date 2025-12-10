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
        for (Location loc : locations) {
            WeatherDTO temp = openWeatherMapService.getWeatherByCoordinates(loc.getLatitude().doubleValue(),
                    loc.getLongitude().doubleValue());
            resultList.add(temp);
        }
        return resultList;
    }

}
