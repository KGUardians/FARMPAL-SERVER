package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ListExperienceDto {
    private Long postId;
    private String sigungu;
    private CropCategory cropCategory;
    private String farmName;
    private int postLike;
    private ImageDto image;

    public ListExperienceDto(Long postId, String sigungu, CropCategory cropCategory, String farmName, int postLike){
        this.postId = postId;
        this.sigungu =sigungu;
        this.cropCategory = cropCategory;
        this.farmName = farmName;
        this.postLike = postLike;
    }


    public static ListExperienceDto toDto(ExperiencePost post){
        return new ListExperienceDto(
                post.getId(),
                post.getAuthor().getAddress().getSigungu(),
                post.getCropCategory(),
                post.getFarmName(),
                post.getPostLike()
        );
    }
}
