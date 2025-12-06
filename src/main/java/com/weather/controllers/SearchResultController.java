package com.weather.controllers;

import com.weather.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class SearchResultController {


    @GetMapping("/search-results")
    public String showSearchResultPage(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Model model) throws IOException {
        return "search-results";
    }

}
