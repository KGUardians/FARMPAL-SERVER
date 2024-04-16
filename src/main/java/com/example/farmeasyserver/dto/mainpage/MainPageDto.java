package com.example.farmeasyserver.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MainPageDto {
    private List<MainComPostDto> communityDtoList;
    private List<ImagePostDto> marketDtoList;
    private List<ImagePostDto> ruralExpDtoList;

}
