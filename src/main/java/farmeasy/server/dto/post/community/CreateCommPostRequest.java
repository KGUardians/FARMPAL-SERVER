package farmeasy.server.dto.post.community;

import farmeasy.server.dto.post.CreatePostRequest;
import farmeasy.server.entity.board.community.CommunityPost;
import farmeasy.server.entity.board.community.CommunityType;
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
