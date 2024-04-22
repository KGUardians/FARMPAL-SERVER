package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.dto.CropPestDto;
import com.example.farmeasyserver.entity.pestdetection.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop,Long> {
    @Query("select new com.example.farmeasyserver.dto.CropPestDto(c.name, cp.pestName, cp.description) " +
            "from Crop c " +
            "join c.pests cp " +
            "where c.id = :cropId")
    List<CropPestDto> findPestsByCrop(Long cropId);
}
