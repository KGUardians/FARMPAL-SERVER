package com.example.farmeasyserver.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MainPageDto {

    private List<CommunityDto> communityDtoList;
    private List<MarketDto> marketDtoList;
    private List<RuralExpDto> ruralExpDtoList;

}
