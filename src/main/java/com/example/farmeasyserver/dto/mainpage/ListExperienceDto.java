package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.post.ListPostDto;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class ListExperienceDto extends ListPostDto {
    private String sigungu;
    private String farmName;
    private String startTime;

    public ListExperienceDto(Long postId,int postLike, String sigungu, CropCategory cropCategory, String farmName, String startTime){
        super(postId,postLike,cropCategory);
        this.sigungu = sigungu;
        this.farmName = farmName;
        this.startTime = startTime;
    }


    public static ListExperienceDto toDto(ExperiencePost post){
        return new ListExperienceDto(
                post.getId(),
                post.getPostLike(),
                post.getAuthor().getAddress().getSigungu(),
                post.getCropCategory(),
                post.getRecruitment().getFarmName(),
                post.getRecruitment().getStartTime()
        );
    }

}
