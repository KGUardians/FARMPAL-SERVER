package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String userName);

}
