package com.weather.dao;

import com.weather.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao<User, Integer, String> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }


    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User WHERE u.login=:username",
                User.class);
        query.setParameter("username", username);
        try {
        return Optional.of(query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
        //TODO тут закончил
    }

    //TODO кастомные ексешены уже стоит делать?
    @Override
    public boolean existByUsername(String username) {
        return false;
    }
}
