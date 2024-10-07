package farmeasy.server.post.dto.community;

import farmeasy.server.post.domain.community.CommunityType;
import farmeasy.server.post.dto.UpdatePostRequest;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateCommPostReq extends UpdatePostRequest {
    private CommunityType type;
}
