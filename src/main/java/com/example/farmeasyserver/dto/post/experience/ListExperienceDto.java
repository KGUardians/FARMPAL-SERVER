package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.dto.mainpage.ListPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class ListExperienceDto extends ListPostDto {
    private String title;
    private String sigungu;
    private String farmName;
    private String startTime;

    public ListExperienceDto(Long postId, int postLike, String title, String sigungu, CropCategory cropCategory, String farmName, String startTime){
        super(postId,postLike,cropCategory);
        this.title = title;
        this.sigungu = sigungu;
        this.farmName = farmName;
        this.startTime = startTime;
    }
}
