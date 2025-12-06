package com.weather.dao;

import com.weather.entity.User;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public Optional<User> findById(int id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id=:username",
                User.class);
        query.setParameter("id",id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.login=:username",
                User.class);
        query.setParameter("username", username);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existByUsername(String username) {
        return findByUsername(username).isPresent();
    }
}
