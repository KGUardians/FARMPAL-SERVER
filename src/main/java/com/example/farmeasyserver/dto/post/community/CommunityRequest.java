package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.post.PostCreateRequest;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @ApiModelProperty(value = "커뮤니티 타입",required = true, example = "FREE")
    @NotNull(message = "게시글 타입 선택")
    private CommunityType type;

}
