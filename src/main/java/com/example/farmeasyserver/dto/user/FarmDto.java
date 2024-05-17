package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.Address;
import com.example.farmeasyserver.entity.user.Farm;
import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FarmDto {
    private String farmName;
    private Address address;

    public static FarmDto toDto(Farm farm){
        return new FarmDto(farm.getFarmName(), farm.getFarmAddress());
    }
}
