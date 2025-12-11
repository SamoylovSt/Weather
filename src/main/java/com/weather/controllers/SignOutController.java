package com.weather.controllers;

import com.weather.entity.Session;
import com.weather.service.SessionService;
import com.weather.util.SessionInterceptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Slf4j
@Controller
public class SignOutController {
    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Autowired
    private SessionService sessionService;

    @PostMapping("/logout")
    public String deleteSession(HttpServletRequest request) {
        String sessionId = sessionInterceptor.getSessionId(request);
        sessionService.deleteSession(sessionId);
        //удалить сессию с этим йд
        return "redirect:/sign-in";
    }
}


