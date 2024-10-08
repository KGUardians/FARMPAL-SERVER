package com.example.farmeasyserver.file.service;

import farmeasy.server.file.service.FileServiceImpl;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FileServiceTest {

    private FileServiceImpl fileService;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileService = new FileServiceImpl();
    }

    @Test
    void testUpload() throws IOException, FileUploadException {
        // Given
        String fileName = "testFile.txt";
        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);

        // When
        fileService.upload(multipartFile, fileName);

        // Then
        verify(multipartFile).transferTo(any(File.class));
    }

    @Test
    void testDelete() {
        // Given
        String fileName = "testFile.txt";
        File mockFile = mock(File.class);

        // When
        fileService.delete(fileName);

        // Then
        verify(mockFile).delete();
    }

    @Test
    void testUploadThrowsException() throws IOException {
        // Given
        String fileName = "testFile.txt";
        doThrow(new IOException("IOException")).when(multipartFile).transferTo(any(File.class));

        // When & Then
        assertThrows(FileUploadException.class, () -> fileService.upload(multipartFile, fileName));
    }
}
