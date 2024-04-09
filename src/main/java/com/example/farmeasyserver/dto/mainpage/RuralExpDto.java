package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.entity.user.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuralExpDto {
    private String title;
    private int like;
    private String author;
    private Address address;
}
