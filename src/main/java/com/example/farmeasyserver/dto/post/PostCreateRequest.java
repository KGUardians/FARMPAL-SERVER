package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.CropCategory;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class PostCreateRequest {
    @ApiModelProperty(value = "게시글 제목", notes = "게시글 제목을 입력해주세요", required = true, example = "my title")
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(value = "카테고리", notes = "카테고리를 입력해주세요", required = true, example = "MARKET")
    @NotNull(message = "카테고리 아이디를 입력해주세요.")
    @PositiveOrZero(message = "올바른 카테고리 아이디를 입력해주세요.")
    private CropCategory cropCategory;

    @Lob
    private String content;

    @ApiModelProperty(value = "이미지", notes = "이미지를 첨부해주세요.")
    private List<MultipartFile> imageList = new ArrayList<>();
}
