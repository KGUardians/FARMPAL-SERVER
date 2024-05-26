package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserJpaRepo extends JpaRepository<User,Long> {

    @Query("select u from User u join fetch u.farm where u.id = :userId")
    Optional<User> findByIdWithFarm(Long userId);
    @Query("select u from User u join fetch u.farm where u.username = :username")
    Optional<User> findByUsername(String username);

}
