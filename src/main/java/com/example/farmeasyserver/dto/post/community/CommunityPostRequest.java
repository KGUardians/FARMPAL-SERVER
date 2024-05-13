package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "커뮤니티 게시글 생성 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityPostRequest extends CreatePostRequest {

}
