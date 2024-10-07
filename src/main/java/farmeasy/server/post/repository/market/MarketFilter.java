package farmeasy.server.post.repository.market;

import farmeasy.server.post.domain.CropCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarketFilter {
    private CropCategory crop;
}
