package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class MainMarketDto {
    private Long postId;
    private String sigungu;
    private String crop;
    private int price;
    private int gram;
    //private String farmName;
    private int postLike;
    private ImageDto image;

    public static MainMarketDto toDto(MarketPost post){
        return new MainMarketDto(
                post.getId(),
                post.getAuthor().getAddress().getSigungu(),
                post.getItem().getItemName(),
                post.getItem().getPrice(),
                post.getItem().getGram(),
                post.getPostLike(),
                post.getImageList().stream().map(i->ImageDto.toDto(i)).findFirst().orElse(null)
        );
    }
}
