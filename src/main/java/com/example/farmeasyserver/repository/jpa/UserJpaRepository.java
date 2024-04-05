package com.example.farmeasyserver.repository.jpa;

import com.example.farmeasyserver.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User,Long> {
    public User findUserByUsername(String username);
}
