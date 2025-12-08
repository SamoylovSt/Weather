package com.weather.controllers;

import com.weather.service.OpenWeatherMapService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class SearchResultController {

    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    @GetMapping("/search-results")
    public String showSearchResultPage(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Model model) throws IOException {
//TODO  сделать страницу поиска с таймлиф
        openWeatherMapService.getWeatherByCity("moscow");

        return "search-results";
    }

    @PostMapping("/search-results")
    @ResponseBody
    public String searchResult(@RequestParam("name") String city) {

        System.out.println(city);
        return "search-results";
    }

}
