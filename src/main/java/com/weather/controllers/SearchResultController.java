package com.weather.controllers;

import com.weather.dto.LocationDTO;
import com.weather.dto.WeatherDTO;
import com.weather.entity.Location;
import com.weather.entity.User;
import com.weather.service.LocationService;
import com.weather.service.OpenWeatherMapService;
import com.weather.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
public class SearchResultController {
    @Autowired
    private OpenWeatherMapService openWeatherMapService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;

    @GetMapping("/search-results")
    public String showSearchResultPage() {
        return "search-results";
    }

    @PostMapping("/search-results")
    public String searchResult(@RequestParam("name") String city,
                               Model model) {
        List<LocationDTO> list = openWeatherMapService.searchWeatherByCity(city);
        if (list != null) {
            model.addAttribute("locations", list);
        }
        return "search-results";
    }

    @PostMapping("/select-location")
    public String selectLocation(@RequestParam("lat") double latitude,
                                 @RequestParam("lon") double longitude,
                                 @RequestParam("city") String city,
                                 HttpServletRequest request) {
        WeatherDTO weatherByCoordinates = openWeatherMapService.getWeatherByCoordinates(latitude, longitude);
        User user = userService.getCurrentUserFromRequest(request);
        Location location = new Location();
        location.setName(weatherByCoordinates.getCity());
        location.setLatitude(BigDecimal.valueOf(latitude));
        location.setLongitude(BigDecimal.valueOf(longitude));
        location.setUser(user);
        locationService.save(location);
        //TODO добавляет не имя локации а какую то хуиту
        return "redirect:/index";
    }

}
