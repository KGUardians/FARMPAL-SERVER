package farmeasy.server.post.dto;

import farmeasy.server.file.domain.Image;
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
