package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.CropCategory;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class CreatePostRequest {
    @Schema(description = "게시글 제목", example = "my title")
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @Schema(description = "작물 종류")
    @NotNull(message = "작물 종류 선택")
    private CropCategory cropCategory;

    @Schema(description = "게시글 본문", example = "my content")
    @Lob
    private String content;

    @Schema(description = "게시글에 넣을 이미지 리스트")
    private List<MultipartFile> imageList = new ArrayList<>();

}
