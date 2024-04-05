package com.example.farmeasyserver.repository.user;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MariaUserReposirory implements UserRepository{


    private EntityManager em;

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        User user=em.find(User.class,id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByname(String name) {
        List<User> result =em.createQuery("select m from User m where m.name=:name",User.class).setParameter("name",name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select m from User m",User.class).getResultList();
    }
}
