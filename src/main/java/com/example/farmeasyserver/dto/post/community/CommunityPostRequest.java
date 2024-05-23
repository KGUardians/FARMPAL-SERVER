package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "커뮤니티 게시글 생성 요청")
@Data
public class CommunityPostRequest extends CreatePostRequest {


    public static CommunityPost toEntity(CommunityType type){
        return new CommunityPost();
    }
}
