package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.entity.user.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarketDto {
    private String title;
    private int like;
    private int price;
    private String author;
    private Address address;
}
