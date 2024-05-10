package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.mainpage.ListExperienceDto;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class ListPostDto {
    private Long postId;
    private int postLike;
    private CropCategory cropCategory;
    private ImageDto image;

    public ListPostDto(Long postId, int postLike, CropCategory cropCategory) {
        this.postId = postId;
        this.postLike = postLike;
        this.cropCategory = cropCategory;
    }

}
