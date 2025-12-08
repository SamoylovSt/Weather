package com.weather.service;

import com.weather.dto.WeatherDTO;

public interface WeatherService {

    WeatherDTO getWeatherByCity(String city);

    WeatherDTO getWeatherByCoordinates(double longitude, double latitude);
}
