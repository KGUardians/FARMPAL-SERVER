package farmeasy.server.service.crop;

import farmeasy.server.dto.CropPestDto;

import java.util.List;

public interface CropService {
    List<CropPestDto> findPests(Long cropId, List<String> pestList);
}
