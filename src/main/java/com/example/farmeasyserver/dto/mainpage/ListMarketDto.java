package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ListMarketDto {
    private Long postId;
    private String sigungu;
    private String crop;
    private int price;
    private int gram;
    //private String farmName;
    private int postLike;
    private ImageDto image;

    public ListMarketDto(Long postId, String sigungu, String crop, int price, int gram, int postLike){
        this.postId = postId;
        this.sigungu = sigungu;
        this.crop = crop;
        this.price = price;
        this.postLike = postLike;

    }

    public static ListMarketDto toDto(MarketPost post){
        return new ListMarketDto(
                post.getId(),
                post.getAuthor().getAddress().getSigungu(),
                post.getItem().getItemName(),
                post.getItem().getPrice(),
                post.getItem().getGram(),
                post.getPostLike()
        );
    }
}
