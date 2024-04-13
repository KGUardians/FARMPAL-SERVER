package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserService {
    void join (User user);

    Optional<User> findUser(String username);
}
