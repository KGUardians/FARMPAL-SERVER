package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.CropCategory;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class UpdatePostRequest {
    @Schema(description = "게시글 제목")
    private String title;
    @Schema(description = "작물 카테고리")
    private CropCategory cropCategory;
    @Schema(description = "게시글 내용")
    private String content;
    @Schema(description = "추가할 이미지 리스트")
    private List<MultipartFile> addedImages = new ArrayList<>();
    @Schema(description = "삭제할 이미지 아이디 리스트")
    private List<Long> deletedImages = new ArrayList<>();

}
