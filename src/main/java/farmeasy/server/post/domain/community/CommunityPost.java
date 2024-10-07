package farmeasy.server.post.domain.community;

import farmeasy.server.post.domain.Post;
import farmeasy.server.post.domain.PostType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class CommunityPost extends Post {

    @Enumerated(EnumType.STRING)
    private CommunityType communityType;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public CommunityPost() {
        super(PostType.COMMUNITY);
    }

    public CommunityPost(CommunityType communityType){
        super(PostType.COMMUNITY);
        this.communityType = communityType;
    }
}
