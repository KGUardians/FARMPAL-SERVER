package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.ItemCategory;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MainExperienceDto {
    private Long postId;
    private String sigungu;
    private ItemCategory crop;
    private String farmName;
    private int postLike;
    private ImageDto image;

    public MainExperienceDto(Long postId,String sigungu, ItemCategory crop,String farmName,int postLike){
        this.postId = postId;
        this.sigungu =sigungu;
        this.crop = crop;
        this.farmName = farmName;
        this.postLike = postLike;
    }


    public static MainExperienceDto toDto(ExperiencePost post){
        return new MainExperienceDto(
                post.getId(),
                post.getAuthor().getAddress().getSigungu(),
                post.getCrop(),
                post.getFarmName(),
                post.getPostLike()
        );
    }
}
