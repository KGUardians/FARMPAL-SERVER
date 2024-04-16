package com.example.farmeasyserver.service.file;

import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Value("${post.image.path}")
    String location;

    @PostConstruct
    void postConstruct() { // 2
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

    }
}
