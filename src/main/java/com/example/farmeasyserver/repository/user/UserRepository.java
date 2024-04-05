package com.example.farmeasyserver.repository.user;

import com.example.farmeasyserver.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component

public interface UserRepository {

    User save(User user);

    Optional<User> findById(int userId);

    Optional<User> findByname(String name);

    List<User> findAll();
}
