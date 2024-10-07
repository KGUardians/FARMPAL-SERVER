package farmeasy.server.post.repository.community;

import farmeasy.server.post.domain.CropCategory;
import farmeasy.server.post.domain.community.CommunityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommunityFilter {
    private CommunityType type;
    private CropCategory crop;
    private String search;

}
