package farmeasy.server.file.service;

import farmeasy.server.main.dto.PostListDto;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    void upload(MultipartFile file, String fileName) throws FileUploadException;
    void delete(String fileName);
    <T extends PostListDto> void imageMapping(List<T> mainPageDto);

}
