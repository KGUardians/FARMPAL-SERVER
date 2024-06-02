package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.experience.CreateExpPostRequest;
import com.example.farmeasyserver.dto.post.experience.ExperienceListDto;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostDto;
import com.example.farmeasyserver.dto.post.experience.UpdateExpPostReq;
import com.example.farmeasyserver.dto.post.experience.expapplication.ExpApplicationPageDto;
import com.example.farmeasyserver.dto.post.experience.expapplication.ExpApplicationRequest;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.experience.ExpFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ExperiencePostService {

    List<ExperienceListDto> getRecentExperiencePostDtos();

    CreatePostResponse createExperiencePost(CreateExpPostRequest req, User user);

    Long deleteExperiencePost(Long postId, User user);

    ExperiencePostDto readExperiencePost(Long postId);

    ExperiencePostDto updateExperiencePost(Long postId, UpdateExpPostReq req, User user);

    Slice<ExperienceListDto> getExperiencePosts(ExpFilter filter, Pageable pageable);

    ExpApplicationPageDto getExpAppPage(Long postId);

    ExpApplicationRequest requestExpApp(Long postId, ExpApplicationRequest req, User applicants) throws Exception;

}
