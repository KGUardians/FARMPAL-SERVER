package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.post.CommunityPostDto;
import com.example.farmeasyserver.dto.post.MarketPostDto;
import com.example.farmeasyserver.dto.post.RuralExpPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MainPageDto {

    private List<MainComPostDto> communityDtoList;
    private List<MarAndRuralPostDto> marketDtoList;
    private List<MarAndRuralPostDto> ruralExpDtoList;

}
