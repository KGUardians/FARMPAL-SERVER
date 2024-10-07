package com.example.farmeasyserver.post.domain.experience;

import farmeasy.server.post.domain.PostType;
import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.domain.exprience.Recruitment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExperiencePostTest {

    private ExperiencePost experiencePost;
    private Recruitment recruitment;

    @BeforeEach
    void setUp() {
        // Recruitment 설정
        recruitment = new Recruitment();
        recruitment.setRecruitmentNum(10); // 모집 인원 설정

        // ExperiencePost 객체 생성
        experiencePost = new ExperiencePost(recruitment);
    }

    @Test
    void testExperiencePostCreation() {
        // ExperiencePost가 정상적으로 생성되었는지 확인
        assertEquals(PostType.EXPERIENCE, experiencePost.getPostType());
        assertNotNull(experiencePost.getRecruitment());
        assertEquals(10, experiencePost.getRecruitment().getRecruitmentNum());
    }

    @Test
    void testValidateParticipantsSuccess() {
        // 참가자 수가 모집 인원 내에 있을 때
        boolean isValid = experiencePost.validateParticipants(5);
        assertTrue(isValid);
    }

    @Test
    void testValidateParticipantsFail() {
        // 참가자 수가 모집 인원보다 많을 때
        boolean isValid = experiencePost.validateParticipants(15);
        assertFalse(isValid);
    }
}
