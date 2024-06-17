package farmeasy.server.dto;

import farmeasy.server.entity.board.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private Long postId;
    private String originName;
    private String uniqueName;

    public static ImageDto toDto(Image image,Long postId){
        return new ImageDto(
                image.getId(),
                postId,
                image.getOriginName(),
                image.getUniqueName()
        );
    }
}
