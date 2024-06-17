package farmeasy.server.dto.mainpage;

import farmeasy.server.dto.ImageDto;
import farmeasy.server.entity.board.CropCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class PostListDto {
    private Long postId;
    private int postLike;
    private CropCategory cropCategory;
    private ImageDto image;

    public PostListDto(Long postId, int postLike, CropCategory cropCategory) {
        this.postId = postId;
        this.postLike = postLike;
        this.cropCategory = cropCategory;
    }

}
