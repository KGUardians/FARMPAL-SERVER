package com.example.farmeasyserver.repository.jpa;

import com.example.farmeasyserver.entity.pestdetection.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropJpaRepository extends JpaRepository<Crop,Long> {

}
