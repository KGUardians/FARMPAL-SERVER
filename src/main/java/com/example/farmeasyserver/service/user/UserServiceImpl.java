package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.repository.jpa.UserJpaRepository;
import com.example.farmeasyserver.repository.user.UserRepository;
import com.example.farmeasyserver.entity.user.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void join(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUser(String username) {
        return userJpaRepository.findUserByUsername(username);
    }
}
