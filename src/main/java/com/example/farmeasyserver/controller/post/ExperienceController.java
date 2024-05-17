package com.example.farmeasyserver.controller.post;

import com.example.farmeasyserver.dto.post.experience.ExpApplicationRequest;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostRequest;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.experience.ExpFilter;
import com.example.farmeasyserver.service.post.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("experience")
@RequiredArgsConstructor
public class ExperienceController {
    private final PostService postService;

    @GetMapping
    public Response readExperiencePostList(@RequestParam(value = "sido", required = false) String sido,
                                           @RequestParam(value = "sigungu", required = false) String sigungu,
                                           Pageable pageable){
        ExpFilter filter = new ExpFilter(sido,sigungu);
        return Response.success(postService.getExperiencePostList(filter, pageable));
    }

    @PostMapping("/post")
    public Response create(@Valid @ModelAttribute ExperiencePostRequest req, @AuthenticationPrincipal User author) {
        return Response.success(postService.createExperiencePost(req, author));
    }

    @ApiOperation(value = "농촌체험 해당 게시글 조회", notes = "게시글을 조회한다.")
    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        return Response.success(postService.readExperiencePost(postId));
    }

    @GetMapping("/application/{postId}")
    public Response applicationPage(@PathVariable Long postId){
        return Response.success(postService.experiencePage(postId));
    }

    @PostMapping("/application/{postId}")
    public Response applicationRequest(@PathVariable Long postId, @RequestBody ExpApplicationRequest req) throws Exception {
        req.setPostId(postId);
        return Response.success(postService.requestExperience(req));
    }
}
