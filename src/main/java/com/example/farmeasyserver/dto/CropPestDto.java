package com.example.farmeasyserver.dto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CropPestDto {
    private String cropName;
    private String pestName;
    @Lob
    private String pestDescription;
}
