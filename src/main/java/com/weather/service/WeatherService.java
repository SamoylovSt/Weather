package com.weather.service;

import com.weather.dto.LocationDTO;
import com.weather.dto.WeatherDTO;

import java.util.List;

public interface WeatherService {

    WeatherDTO getWeatherByCity(String city);

    WeatherDTO getWeatherByCoordinates(double latitude, double longitude);

    List<LocationDTO> searchWeatherByCity(String city);
}
