package farmeasy.server.dto.post.community;

import farmeasy.server.dto.post.UpdatePostRequest;
import farmeasy.server.entity.board.community.CommunityType;
import lombok.Data;


@Data
public class UpdateCommPostReq extends UpdatePostRequest {
    private CommunityType type;
}
