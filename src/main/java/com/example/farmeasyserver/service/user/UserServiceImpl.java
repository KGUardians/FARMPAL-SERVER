package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.repository.UserRepository;
import com.example.farmeasyserver.repository.jpa.UserJpaRepository;
import com.example.farmeasyserver.entity.user.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserRepository UserRepository;

    @Override
    public void join(User user) {
        UserRepository.save(user);
    }

    @Override
    public Optional<User> findUser(String username) {
        return UserRepository.findByname(username);
    }
}
