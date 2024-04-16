package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;


public interface UserService {
    void join (User user);

    User findUser(String username);

    Optional<User> getUser(Long id);
}
