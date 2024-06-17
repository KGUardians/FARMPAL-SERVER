package farmeasy.server.repository.post.community;

import farmeasy.server.entity.board.CropCategory;
import farmeasy.server.entity.board.community.CommunityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommunityFilter {
    private CommunityType type;
    private CropCategory crop;
    private String search;

}
