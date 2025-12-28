package com.weather.service;

import com.weather.dto.LocationDTO;
import com.weather.dto.WeatherDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OpenWeatherMapService implements WeatherService {
    @Value("${open.weather.url}")
    private String URL;
    @Value("${open.weather.apy-key}")
    private String APY_KEY;
    @Value("${open.weather.url-for-location-list}")
    private String URL_FOR_LOCATION_LIST;
    private final String LIMIT = "5";
    private final String REQUEST_FOR_GET_WEATHER_BY_COORDINATES = "%s?lat=%s&lon=%s&appid=%s&units=metric";
    private final String REQUEST_FOR_SEARCH_WEATHER_BY_CITY = "%s?q=%s&limit=%s&appid=%s";

    @Override
    public WeatherDTO getWeatherByCoordinates(double latitude, double longitude) {
        log.info("Get location by coordinates latitude: {}, longitude: {}", latitude, longitude);
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(REQUEST_FOR_GET_WEATHER_BY_COORDINATES, URL, latitude, longitude, APY_KEY);
        WeatherDTO result = restTemplate.getForObject(url, WeatherDTO.class);
        return result;
    }

    @Override
    public List<LocationDTO> searchWeatherByCity(String city) {
        log.info("Search weather by city: {}", city);
        RestTemplate restTemplate = new RestTemplate();
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = String.format(REQUEST_FOR_SEARCH_WEATHER_BY_CITY, URL_FOR_LOCATION_LIST, encodedCity, LIMIT, APY_KEY);
        LocationDTO[] locations = restTemplate.getForObject(url, LocationDTO[].class);
        List<LocationDTO> resultList = new ArrayList<>();
        for (LocationDTO l : locations) {
            resultList.add(l);
        }
        return resultList;
    }
}
