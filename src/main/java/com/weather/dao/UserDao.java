package com.weather.dao;

import com.weather.entity.User;

import java.util.Optional;

public interface UserDao<E,I,S> {
    void save(E user);
    Optional<User> findById(I id);
    Optional<User> findByUsername(S username);
    boolean existByUsername(S username);
    //TODO учусь работать с Optional<User>
}
