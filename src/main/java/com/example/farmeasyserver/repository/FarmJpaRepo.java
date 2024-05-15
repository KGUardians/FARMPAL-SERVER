package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.entity.user.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmJpaRepo extends JpaRepository<Farm,Long> {
}
