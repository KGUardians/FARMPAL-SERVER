package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.repository.UserRepository;
import com.example.farmeasyserver.repository.jpa.UserJpaRepository;
import com.example.farmeasyserver.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void join(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public User findUser(String userName) {
        return userJpaRepository.findByUsername(userName);
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userJpaRepository.findById(id);
    }
}
