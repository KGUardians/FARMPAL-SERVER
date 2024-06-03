package farmeasy.server.dto.post;

import farmeasy.server.entity.board.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateImageResult {
    private List<MultipartFile> addedImageFileList;
    private List<Image> addedImageList;
    private List<Image> deletedImageList;

}
