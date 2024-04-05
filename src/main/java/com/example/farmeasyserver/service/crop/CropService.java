package com.example.farmeasyserver.service.crop;

import com.example.farmeasyserver.dto.CropPestDto;

import java.util.List;

public interface CropService {
    List<CropPestDto> findPests(Long cropId, List<String> pestList);
}
