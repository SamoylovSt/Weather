package com.weather.service;

import com.weather.dto.WeatherDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class OpenWeatherMapService implements WeatherService {

    private final String URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String APY_KEY = "5aca02bb848cd4b86a8bcb82945b4b76";

    @Override
    public WeatherDTO getWeatherByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = String.format("%s?q=%s&appid=%s", URL, encodedCity, APY_KEY);
        WeatherDTO weatherDTO = restTemplate.getForObject(url, WeatherDTO.class);
        return weatherDTO;
    }

    @Override
    public WeatherDTO getWeatherByCoordinates(double longitude, double latitude) {
        return null;
    }

}
