package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.post.community.ListCommunityDto;
import com.example.farmeasyserver.dto.post.experience.ListExperienceDto;
import com.example.farmeasyserver.dto.post.market.ListMarketDto;
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
