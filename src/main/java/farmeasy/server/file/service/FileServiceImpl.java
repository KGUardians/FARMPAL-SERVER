package farmeasy.server.file.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value(value = "${post.image.path}")
    String location;

    @PostConstruct
    void postConstruct() {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void upload(MultipartFile file, String fileName) throws FileUploadException {
        try {
            file.transferTo(new File(location + fileName));
        } catch(IOException e) {
            throw new FileUploadException("파일을 업로드할 수 없습니다.");
        }
    }

    @Override
    public void delete(String fileName) {
        File file = new File(location + fileName);
        if (!file.delete()) {
            throw new RuntimeException("파일을 삭제할 수 없습니다: " + fileName);
        }
    }
}