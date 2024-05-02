package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.post.PostCreateRequest;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "커뮤니티 게시글 생성 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityRequest extends PostCreateRequest {

    @ApiModelProperty(value = "게시글 본문", notes = "게시글 본문을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String content;

    private CommunityType type;
    private CropCategory cropCategory;

}
