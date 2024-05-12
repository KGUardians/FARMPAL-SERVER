package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "커뮤니티 게시글 생성 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityPostRequest extends CreatePostRequest {

    @ApiModelProperty(value = "커뮤니티 타입",required = true, example = "FREE")
    @NotNull(message = "게시글 타입 선택")
    private CommunityType type;

}
