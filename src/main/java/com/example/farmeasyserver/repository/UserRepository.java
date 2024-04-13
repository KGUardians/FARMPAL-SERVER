package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }


    public Optional<User> findById(int id) {
        User user=em.find(User.class,id);
        return Optional.ofNullable(user);
    }


    public Optional<User> findByname(String name) {
        List<User> result =em.createQuery("select m from User m where m.name=:name",User.class).setParameter("name",name).getResultList();
        return result.stream().findAny();
    }


    public List<User> findAll() {
        return em.createQuery("select m from User m",User.class).getResultList();
    }
}
