package farmeasy.server.repository.post.market;

import farmeasy.server.entity.board.CropCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarketFilter {
    private CropCategory crop;
}
