package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.CropCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class PostListDto {
    private Long postId;
    private int postLike;
    private CropCategory cropCategory;
    private ImageDto image;

    public PostListDto(Long postId, int postLike, CropCategory cropCategory) {
        this.postId = postId;
        this.postLike = postLike;
        this.cropCategory = cropCategory;
    }

}
