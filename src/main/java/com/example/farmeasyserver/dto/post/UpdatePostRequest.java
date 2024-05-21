package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.CropCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class UpdatePostRequest {
    private String title;
    private CropCategory cropCategory;
    private String content;
    @ApiModelProperty(value = "추가된 이미지", notes = "추가된 이미지를 첨부")
    private List<MultipartFile> addedImages = new ArrayList<>();
    @ApiModelProperty(value = "삭제할 이미지 아이디", notes = "삭제할 이미지 아이디 입력")
    private List<Long> deletedImages = new ArrayList<>();

}
