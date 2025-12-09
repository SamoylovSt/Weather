package com.weather.controllers;

import com.weather.dto.LocationDTO;
import com.weather.dto.WeatherDTO;
import com.weather.service.OpenWeatherMapService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class SearchResultController {

    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    @GetMapping("/search-results")
    public String showSearchResultPage() {

        return "search-results";
    }

    @PostMapping("/search-results")
    public String searchResult(@RequestParam("name") String city,
                               Model model) {
//        WeatherDTO weatherDTO = openWeatherMapService.getWeatherByCity(city);
//        System.out.println(weatherDTO);
//        model.addAttribute("");

        List<LocationDTO> list = openWeatherMapService.searchWeatherByCity(city);
        System.out.println(list);
        return "search-results";
    }

}
