package farmeasy.server.post.controller;

import farmeasy.server.dto.response.Response;
import farmeasy.server.post.dto.experience.CreateExpPostRequest;
import farmeasy.server.post.dto.experience.UpdateExpPostReq;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationRequest;
import farmeasy.server.post.service.experience.ExpApplicationService;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.experience.ExpFilter;
import farmeasy.server.post.service.experience.ExperiencePostService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("experience")
@RequiredArgsConstructor
public class ExperienceController {
    private final ExperiencePostService experiencePostService;
    private final ExpApplicationService expApplicationService;

    @GetMapping
    @Operation(summary = "농촌체험 게시글 리스트 불러오기")
    public Response getExperiencePostList(
            @RequestParam(value = "sido", required = false) String sido,
            @RequestParam(value = "sigungu", required = false) String sigungu,
            Pageable pageable
    ){
        ExpFilter filter = new ExpFilter(sido,sigungu);
        return Response.success(experiencePostService.getExperiencePosts(filter, pageable));
    }

    @PostMapping
    @Operation(summary = "농촌체험 게시글 작성")
    public Response createPost(
            @Valid @RequestPart CreateExpPostRequest req,
            @AuthenticationPrincipal User author
    ) {
        return Response.success(experiencePostService.createExperiencePost(req, author));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 삭제")
    public Response deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User author
    ) {
        return Response.success(experiencePostService.deleteExperiencePost(postId, author));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 조회")
    public Response readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        return Response.success(experiencePostService.readExperiencePost(postId));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 수정")
    public Response updatePost(
            @PathVariable Long postId,
            @Valid @ModelAttribute UpdateExpPostReq req,
            @AuthenticationPrincipal User author
    ){
        return Response.success(experiencePostService.updateExperiencePost(postId, req, author));
    }

    @GetMapping("/{postId}/application")
    @Operation(summary = "해당 체험 게시글 신청 폼")
    public Response getExpAppPage(@PathVariable Long postId){
        return Response.success(expApplicationService.getExpAppPage(postId));
    }

    @PostMapping("/{postId}/application")
    @Operation(summary = "해당 체험 게시글 신청 요청")
    public Response requestExpApp(
            @PathVariable Long postId,
            @RequestBody ExpApplicationRequest req,
            @AuthenticationPrincipal User applicant
    ) throws Exception {
        return Response.success(expApplicationService.requestExpApp(postId, req, applicant));
    }

}
