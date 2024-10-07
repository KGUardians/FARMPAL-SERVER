package com.example.farmeasyserver.file.repository;

import farmeasy.server.file.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public class FileFakeRepository implements FileService {
    private final Map<String, String> storedFiles = new HashMap<>(); // 메모리 내 파일 저장소

    @Override
    public void upload(MultipartFile file, String uniqueName) {
        // 업로드된 파일을 메모리에 저장
        storedFiles.put(uniqueName, file.getOriginalFilename());
    }

    @Override
    public void delete(String uniqueName) {
        // 메모리 내에서 파일 삭제
        storedFiles.remove(uniqueName);
    }

    // 테스트용 메서드: 파일이 저장되어 있는지 확인
    public boolean isFileStored(String uniqueName) {
        return storedFiles.containsKey(uniqueName);
    }

    // 테스트용 메서드: 저장된 파일 개수 확인
    public int getStoredFileCount() {
        return storedFiles.size();
    }
}
