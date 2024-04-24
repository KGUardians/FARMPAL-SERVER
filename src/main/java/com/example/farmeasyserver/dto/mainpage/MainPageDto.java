package com.example.farmeasyserver.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MainPageDto {
    List<MainCommunityDto> mainCommunityDtoList;
    List<MainMarketDto> mainMarketDtoList;
    List<MainExperienceDto> mainExperienceDtoList;

}
