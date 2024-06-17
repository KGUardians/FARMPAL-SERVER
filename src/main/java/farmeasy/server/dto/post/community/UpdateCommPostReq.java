package farmeasy.server.dto.post.community;

import farmeasy.server.dto.post.UpdatePostRequest;
import farmeasy.server.entity.board.community.CommunityType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateCommPostReq extends UpdatePostRequest {
    private CommunityType type;
}
