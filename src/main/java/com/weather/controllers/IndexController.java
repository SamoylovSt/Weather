package com.weather.controllers;

import com.weather.dto.WeatherDTO;
import com.weather.entity.Location;
import com.weather.entity.User;
import com.weather.service.LocationService;
import com.weather.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        System.out.println(list);
        List<WeatherDTO> weatherList = locationService.getLocationListForIndex(list);
        System.out.println(weatherList);

        model.addAttribute("weatherList", weatherList);
        //лист с локациями я получил, надо разобраться с фронтом и динамической иконкой

        return "index";
    }

}
