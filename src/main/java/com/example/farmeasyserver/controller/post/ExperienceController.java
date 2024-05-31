package com.example.farmeasyserver.controller.post;

import com.example.farmeasyserver.dto.post.experience.expapplication.ExpApplicationRequest;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostRequest;
import com.example.farmeasyserver.dto.post.experience.UpdateExpPostReq;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.experience.ExpFilter;
import com.example.farmeasyserver.service.post.PostService;
import com.example.farmeasyserver.service.user.UserService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("experience")
@RequiredArgsConstructor
public class ExperienceController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "농촌체험 게시글 리스트 불러오기")
    public Response getExperiencePostList(@RequestParam(value = "sido", required = false) String sido,
                                           @RequestParam(value = "sigungu", required = false) String sigungu,
                                           Pageable pageable){
        ExpFilter filter = new ExpFilter(sido,sigungu);
        return Response.success(postService.getExperiencePostList(filter, pageable));
    }

    @PostMapping
    @Operation(summary = "농촌체험 게시글 작성")
    public Response createPost(@Valid @ModelAttribute ExperiencePostRequest req) {
        User author = userService.getByUsername();
        return Response.success(postService.createExperiencePost(req, author));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 삭제")
    public Response deletePost(@PathVariable Long postId){
        User author = userService.getByUsername();
        return Response.success(postService.deleteExperiencePost(postId, author));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 조회")
    public Response readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        return Response.success(postService.readExperiencePost(postId));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 수정")
    public Response updatePost(@PathVariable Long postId, @Valid @ModelAttribute UpdateExpPostReq req){
        User author = userService.getByUsername();
        return Response.success(postService.updateExperiencePost(postId, req, author));
    }

    @GetMapping("/{postId}/application")
    @Operation(summary = "해당 체험 게시글 신청 폼")
    public Response getExpAppPage(@PathVariable Long postId){
        return Response.success(postService.getExpAppPage(postId));
    }

    @PostMapping("/{postId}/application")
    @Operation(summary = "해당 체험 게시글 신청 요청")
    public Response requestExpApp(@PathVariable Long postId, @RequestBody ExpApplicationRequest req) throws Exception {
        User user = userService.getByUsername();
        return Response.success(postService.requestExpApp(postId, req, user));
    }

}
