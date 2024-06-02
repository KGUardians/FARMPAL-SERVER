package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "커뮤니티 게시글 생성 요청")
@Data
public class CreateCommPostRequest extends CreatePostRequest {


    private CommunityType communityType;
  
    public static CommunityPost toEntity(CommunityType communityType){
        return new CommunityPost(communityType);
    }
}
