package com.weather.controllers;

import com.weather.dto.WeatherDTO;
import com.weather.entity.Location;
import com.weather.entity.User;
import com.weather.service.LocationService;
import com.weather.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/index")
    public String showIndexPage(HttpServletRequest request,
                                Model model) {
        User currentUser = userService.getCurrentUserFromRequest(request);
        List<Location> list = locationService.getLocationsForCurrentUser(currentUser.getId());
        log.info(list + " list from getLocationsForCurrentUser");
        List<WeatherDTO> weatherList = locationService.getLocationListForIndex(list);
        log.info(weatherList + " getLocationListForIndex");
        model.addAttribute("weatherList", weatherList);
        return "index";
    }

    @PostMapping("/delete-location")
    public String deleteLocation(@RequestParam("city") String city) {
        Location location = locationService.findLocationByCity(city);
        log.info(location + " location found");
        locationService.deleteLocation(city.split(" ")[0]);
        return "redirect:/index";
    }
}
