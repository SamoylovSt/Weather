package com.weather.service;

import com.weather.dao.SessionDao;
import com.weather.dao.UserDao;
import com.weather.entity.Session;
import com.weather.entity.User;
import com.weather.util.BCyptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionService sessionService;

    @Autowired
    private BCyptPasswordEncoder encoder;

    public void save(User user) {
        userDao.save(user);
    }

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
        User user = userOptional.get();
        if (!userOptional.isPresent()) {
            return false;
        }
        return encoder.matches(password, user.getPassword());
    }

}
