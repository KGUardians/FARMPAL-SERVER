package com.example.farmeasyserver.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MainPageDto {
    List<ListCommunityDto> listCommunityDtoList;
    List<ListMarketDto> listMarketDtoList;
    List<ListExperienceDto> listExperienceDtoList;

}
