package com.example.farmeasyserver.dto;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class CropPestDto {
    private String cropName;
    private String pestName;
    @Lob
    private String pestDescription;
}
