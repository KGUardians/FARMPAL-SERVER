package farmeasy.server.repository;

import farmeasy.server.dto.CropPestDto;
import farmeasy.server.entity.pestdetection.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CropJpaRepo extends JpaRepository<Crop,Long> {
    @Query("select new farmeasy.server.dto.CropPestDto(c.name, cp.pestName, cp.description) " +
            "from Crop c " +
            "join c.pests cp " +
            "where c.id = :cropId")
    List<CropPestDto> findPestsByCrop(Long cropId);
}
