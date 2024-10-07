package farmeasy.server.dto.post.community;

import farmeasy.server.post.domain.community.CommunityPost;
import farmeasy.server.post.domain.community.CommunityType;
import farmeasy.server.post.dto.CreatePostRequest;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "커뮤니티 게시글 생성 요청")
@Getter
@Setter
public class CreateCommPostRequest extends CreatePostRequest {
    private CommunityType communityType;
  
    public static CommunityPost toEntity(CommunityType communityType){
        return new CommunityPost(communityType);
    }
}
