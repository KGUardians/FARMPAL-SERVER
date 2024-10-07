package com.example.farmeasyserver.file.domain;

import com.example.farmeasyserver.post.domain.TestPost;
import farmeasy.server.file.domain.Image;
import farmeasy.server.post.domain.Post;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @Test
    void testImageCreation() {
        // 정상적인 이미지 이름을 가진 Image 객체 생성
        String validImageName = "example.jpg";
        Image image = new Image(validImageName);

        // originName과 uniqueName이 올바르게 설정되었는지 확인
        assertEquals(validImageName, image.getOriginName());
        assertNotNull(image.getUniqueName());
        assertTrue(image.getUniqueName().endsWith(".jpg"));
    }

    @Test
    void testSetPost() {
        // Image 객체 생성
        String validImageName = "example.png";
        Image image = new Image(validImageName);

        // Post 객체 생성 및 설정
        Post post = new TestPost();
        image.setPost(post);

        // Post가 정상적으로 설정되었는지 확인
        assertEquals(post, image.getPost());
    }

    @Test
    void testUnsupportedFormat() {
        // 지원되지 않는 확장자로 이미지 생성 시 예외 발생 확인
        String invalidImageName = "example.txt";
        assertThrows(UnsupportedOperationException.class, () -> {
            new Image(invalidImageName);
        });
    }

    @Test
    void testSupportedFormat() {
        // 지원되는 확장자로 Image 객체 생성 테스트
        String validImageName = "example.gif";
        Image image = new Image(validImageName);

        // 확장자가 올바르게 처리되었는지 확인
        assertTrue(image.getUniqueName().endsWith(".gif"));
    }
}
