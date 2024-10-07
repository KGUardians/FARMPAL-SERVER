package farmeasy.server.file.dto;

import farmeasy.server.file.domain.Image;
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
