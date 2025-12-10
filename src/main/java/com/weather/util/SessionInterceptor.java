package com.weather.util;

import com.weather.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private ApplicationContext applicationContext;

    private SessionService sessionService;

    private static final String SESSION_COOKIE_NAME = "session";

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (sessionService == null) {
            sessionService = applicationContext.getBean(SessionService.class);
        }

        String sessionId = getSessionId(request);
        if (sessionId == null) {
            response.sendRedirect("/sign-in");
            return false;
        }
        if (sessionService.isSessionExpired(sessionId)) {
            removeSessionCookie(request, response);
            response.sendRedirect("/sign-in");
            return false;
        }
        return true;
    }

    public String getSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void removeSessionCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, "");
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
