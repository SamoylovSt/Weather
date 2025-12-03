package com.weather.dao;

import com.weather.entity.User;

import java.util.Optional;

public interface UserDao {
    void save(User user);
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    boolean existByUsername(String username);
}
