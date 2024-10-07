package com.example.farmeasyserver.post.domain;

import com.example.farmeasyserver.file.repository.FileFakeRepository;
import farmeasy.server.file.domain.Image;
import farmeasy.server.file.service.FileService;
import farmeasy.server.file.service.ImageManager;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageManagerTest {

    private ImageManager imageManager;
    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = mock(FileFakeRepository.class);
        imageManager = new ImageManager(fileService);
    }

    @Test
    void testConvertImageFileToImage() {
        // Given
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        when(file1.getOriginalFilename()).thenReturn("image1.jpg");
        when(file2.getOriginalFilename()).thenReturn("image2.jpg");

        // When
        List<Image> imageList = imageManager.convertImageFileToImage(List.of(file1, file2));

        // Then
        assertEquals(2, imageList.size());
        assertEquals("image1.jpg", imageList.get(0).getOriginName());
        assertEquals("image2.jpg", imageList.get(1).getOriginName());
    }

    @Test
    void testAddImageList() {
        // Given
        TestPost post = new TestPost();
        List<Image> imageList = List.of(new Image("image1.jpg"), new Image("image2.jpg"));

        // When
        imageManager.addImageList(post, imageList);

        // Then
        assertEquals(2, post.getImageList().size());
        assertEquals("image1.jpg", post.getImageList().get(0).getOriginName());
        assertEquals("image2.jpg", post.getImageList().get(1).getOriginName());
    }

    @Test
    void testDeleteImageList() {
        // Given
        TestPost post = new TestPost();
        Image image1 = new Image("image1.jpg");
        Image image2 = new Image("image2.jpg");
        post.getImageList().addAll(List.of(image1, image2));

        // When
        imageManager.deleteImageList(post, List.of(image1));

        // Then
        assertEquals(1, post.getImageList().size());
        assertEquals("image2.jpg", post.getImageList().get(0).getOriginName());
    }

    @Test
    void testUploadAndDeleteImageWithFileService() throws FileUploadException {
        // Given
        TestPost post = new TestPost();
        Image image = new Image("image1.jpg");
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("image1.jpg");

        // When
        imageManager.addImageList(post, List.of(image));
        fileService.upload(file, image.getUniqueName());

        // Then
        verify(fileService, times(1)).upload(file, image.getUniqueName());

        // When (deleting the image)
        imageManager.deleteImageList(post, List.of(image));
        fileService.delete(image.getUniqueName());

        // Then
        verify(fileService, times(1)).delete(image.getUniqueName());
        assertEquals(0, post.getImageList().size());
    }
}
