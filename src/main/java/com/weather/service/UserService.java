package com.weather.service;

import com.weather.dao.UserDao;
import com.weather.entity.Session;
import com.weather.entity.User;
import com.weather.util.BCryptPasswordEncoder;
import com.weather.util.SessionInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public boolean existByUsername(String name) {
        return userDao.existByUsername(name);
    }

    public void createUser(String name, String password, String sessionId) {
        if (userDao.existByUsername(name)) {
            throw new RuntimeException("User already exist" + name);
        }
        String encodePassword = encoder.encode(password);
        User userForSave = new User();
        userForSave.setLogin(name);
        userForSave.setPassword(encodePassword);
        userDao.save(userForSave);
        sessionService.createSession(userForSave, sessionId);
    }

    public boolean authenticate(String name, String password) {
        Optional<User> userOptional = userDao.findByUsername(name);
        if (!userOptional.isPresent()) {
            return false;
        }
        User user = userOptional.get();
        return encoder.matches(password, user.getPassword());
    }

    public User findByUsername(String name) {
        Optional<User> userOptional = userDao.findByUsername(name);
        User user = userOptional.get();
        return user;
    }

    public User findById(int id) {
        Optional<User> userOptional = userDao.findById(id);
        User user = userOptional.get();
        return user;
    }

    public User getCurrentUserFromRequest(HttpServletRequest request) {
        String sessionId = sessionInterceptor.getSessionId(request);
        Optional<Session> sessionOpt = sessionService.findSession(sessionId);
        Session currentSession = sessionOpt.get();
        return findById(currentSession.getUser().getId());
    }

}
