package com.weather.service;

import com.weather.dto.LocationDTO;
import com.weather.dto.WeatherDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenWeatherMapService implements WeatherService {

    private final String URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String APY_KEY = "5aca02bb848cd4b86a8bcb82945b4b76";
    private final String URL_FOR_LOCATION_LIST = "http://api.openweathermap.org/geo/1.0/direct";
    private final String LIMIT = "5";

    @Override
    public WeatherDTO getWeatherByCoordinates(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?lat=%s&lon=%s&appid=%s", URL, latitude, longitude, APY_KEY);
        WeatherDTO result = restTemplate.getForObject(url, WeatherDTO.class);
        return result;
    }

    @Override
    public List<LocationDTO> searchWeatherByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = String.format("%s?q=%s&limit=%s&appid=%s", URL_FOR_LOCATION_LIST, encodedCity, LIMIT, APY_KEY);
        LocationDTO[] locations = restTemplate.getForObject(url, LocationDTO[].class);
        List<LocationDTO> resultList = new ArrayList<>();
        for (LocationDTO l : locations) {
            resultList.add(l);
        }
        return resultList;
    }

}
