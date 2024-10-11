package com.example.farmeasyserver.post.domain.experience;

import farmeasy.server.post.domain.exprience.ExpApplication;
import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.domain.exprience.Recruitment;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationRequest;
import farmeasy.server.post.repository.experience.ExpAppJpaRepo;
import farmeasy.server.post.service.experience.ExpApplicationServiceImpl;
import farmeasy.server.post.service.experience.ExperiencePostService;
import farmeasy.server.user.domain.User;
import farmeasy.server.util.exception.post.experience.ApplicationNotFoundException;
import farmeasy.server.util.exception.post.experience.MaxParticipantsExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExpApplicationServiceImplTest {

    @Mock
    private ExpAppJpaRepo expAppJpaRepo;

    @Mock
    private ExperiencePostService experiencePostService;

    @InjectMocks
    private ExpApplicationServiceImpl expApplicationService;

    private ExperiencePost experiencePost;
    private User applicant;
    private ExpApplicationRequest expApplicationRequest;
    private ExpApplication expApplication;

    @BeforeEach
    void 설정() {
        MockitoAnnotations.openMocks(this);

        experiencePost = new ExperiencePost();
        experiencePost.setRecruitment(new Recruitment(null,null,10, 7,null)); // 모집 인원: 10명

        applicant = new User();
        applicant.setId(1L);

        expApplicationRequest = new ExpApplicationRequest();
        expApplicationRequest.setParticipants(3); // 신청 인원 3명

        expApplication = new ExpApplication(3, applicant, experiencePost);
    }

    @Test
    void 신청_성공() {
        when(experiencePostService.getExperiencePost(1L)).thenReturn(experiencePost);

        expApplicationService.requestExpApp(1L, expApplicationRequest, applicant);

        assertEquals(4, experiencePost.getRecruitment().getRemainingCapacity());
        verify(expAppJpaRepo, times(1)).save(any(ExpApplication.class));
    }

    @Test
    void 신청_인원초과() {
        experiencePost.getRecruitment().reduceCapacity(6); // 이미 9명 신청

        when(experiencePostService.getExperiencePost(1L)).thenReturn(experiencePost);

        assertThrows(MaxParticipantsExceededException.class, () -> {
            expApplicationService.requestExpApp(1L, expApplicationRequest, applicant);
        });

        verify(expAppJpaRepo, times(0)).save(any(ExpApplication.class));
    }

    @Test
    void 신청_삭제_성공() {
        when(expAppJpaRepo.findById(1L)).thenReturn(Optional.of(expApplication));

        expApplicationService.deleteExpApp(1L, applicant);

        verify(expAppJpaRepo, times(1)).delete(expApplication);
        assertEquals(10, experiencePost.getRecruitment().getRemainingCapacity()); // 인원 복구 확인
    }

    @Test
    void 신청_삭제_신청없음() {
        when(expAppJpaRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApplicationNotFoundException.class, () -> {
            expApplicationService.deleteExpApp(1L, applicant);
        });

        verify(expAppJpaRepo, times(0)).delete(any(ExpApplication.class));
    }

    @Test
    void 신청_수정_성공() throws IllegalAccessException {
        ExpApplicationRequest updateRequest = new ExpApplicationRequest();
        updateRequest.setParticipants(5); // 인원을 5명으로 수정

        when(expAppJpaRepo.findById(1L)).thenReturn(Optional.of(expApplication));

        expApplicationService.updateExpApp(1L, updateRequest, applicant);

        assertEquals(5, experiencePost.getRecruitment().getRemainingCapacity()); // 남은 모집 인원 확인
        verify(expAppJpaRepo, times(1)).save(expApplication);
    }

    @Test
    void 신청_수정_인원초과() {
        expApplicationRequest.setParticipants(11); // 변경하려는 인원이 총 모집 가능 인원을 넘김
        when(expAppJpaRepo.findById(1L)).thenReturn(Optional.of(expApplication));

        assertThrows(MaxParticipantsExceededException.class, () -> {
            expApplicationService.updateExpApp(1L, expApplicationRequest, applicant);
        });

        verify(expAppJpaRepo, times(0)).save(any(ExpApplication.class));
    }

}
