package com.weather.service;

import com.weather.dao.UserDaoImpl;
import com.weather.entity.User;
import com.weather.util.BCyptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private BCyptPasswordEncoder encoder;

    public void save(User user) {
        userDao.save(user);
    }

    public boolean existByUsername(String name) {
        return userDao.existByUsername(name);
    }

    public void registerUser(String name, String password) {
        if (userDao.existByUsername(name)) {
            throw new RuntimeException("User already exist" + name);
        }
        String encodePassword = encoder.encode(password);
        User userForSave = new User();
        userForSave.setLogin(name);
        userForSave.setPassword(encodePassword);
        userDao.save(userForSave);
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
