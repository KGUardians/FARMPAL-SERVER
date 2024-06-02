package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.post.community.CommunityListDto;
import com.example.farmeasyserver.dto.post.experience.ExperienceListDto;
import com.example.farmeasyserver.dto.post.market.MarketListDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MainPageDto {
    List<CommunityListDto> listCommunityDtoList;
    List<MarketListDto> listMarketDtoList;
    List<ExperienceListDto> listExperienceDtoList;

}
