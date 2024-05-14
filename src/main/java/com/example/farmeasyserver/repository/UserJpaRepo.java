package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserJpaRepo extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    @Query("select u from User u where u.username = :userName and u.phoneNumber = :phoneNumber")
    Optional<User> findByUsernameAndPhoneNumber(String userName, String phoneNumber);

}
