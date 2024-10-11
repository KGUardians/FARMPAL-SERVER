package farmeasy.server.post.domain.community;

import farmeasy.server.post.domain.Post;
import farmeasy.server.post.domain.PostType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class CommunityPost extends Post {

    @Enumerated(EnumType.STRING)
    private CommunityType communityType;

    public CommunityPost() {
        super(PostType.COMMUNITY);
    }

    public CommunityPost(CommunityType communityType){
        super(PostType.COMMUNITY);
        this.communityType = communityType;
    }
}
