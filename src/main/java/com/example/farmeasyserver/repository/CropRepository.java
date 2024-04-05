package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.dto.CropPestDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CropRepository {
    private final EntityManager em;

    //해당 작물의 모든 병충정보
    public List<CropPestDto> findPestsByCrop(Long cropId){
        return em.createQuery(
                "select c.name,cp.pestName,cp.description from Crop c"+
                        " join fetch c.pests cp"+
                        " where c.id = :cropId",CropPestDto.class)
                .setParameter("cropId",cropId)
                .getResultList();
    }
}
