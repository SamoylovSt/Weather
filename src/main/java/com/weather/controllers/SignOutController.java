package com.weather.controllers;

import com.weather.service.OpenWeatherMapService;
import com.weather.service.SessionService;
import com.weather.util.SessionInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class SignOutController {
    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Autowired
    private SessionService sessionService;


    @PostMapping("/logout")
    public String deleteSession(HttpServletRequest request,
                                HttpServletResponse response) {
        String sessionId = sessionInterceptor.getSessionId(request);
        sessionService.deleteSession(sessionId);
        sessionInterceptor.removeSessionCookie(request,response);
        return "redirect:/sign-in";
    }
}


