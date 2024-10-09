package farmeasy.server.post.controller;

import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.experience.CreateExpPostRequest;
import farmeasy.server.post.dto.experience.ExperienceListDto;
import farmeasy.server.post.dto.experience.ExperiencePostDto;
import farmeasy.server.post.dto.experience.UpdateExpPostReq;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationPageDto;
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
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Slice<ExperienceListDto>> getExperiencePostList(
            @RequestParam(value = "sido", required = false) String sido,
            @RequestParam(value = "sigungu", required = false) String sigungu,
            Pageable pageable
    ){
        ExpFilter filter = new ExpFilter(sido,sigungu);
        Slice<ExperienceListDto> response = experiencePostService
                .getExperiencePosts(filter, pageable);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "농촌체험 게시글 작성")
    public ResponseEntity<CreatePostResponse> createPost(
            @Valid @RequestBody CreateExpPostRequest req,
            @AuthenticationPrincipal User author
    ) {
        CreatePostResponse response = experiencePostService
                .createExperiencePost(req, author);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 삭제")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User author
    ) {
        experiencePostService.deleteExperiencePost(postId, author);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 조회")
    public ResponseEntity<ExperiencePostDto> readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        ExperiencePostDto response = experiencePostService.readExperiencePost(postId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "농촌체험 게시글 수정")
    public ResponseEntity<ExperiencePostDto>updatePost(
            @PathVariable Long postId,
            @Valid @ModelAttribute UpdateExpPostReq req,
            @AuthenticationPrincipal User author
    ){
        ExperiencePostDto response = experiencePostService.updateExperiencePost(postId, req, author);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/application")
    @Operation(summary = "해당 체험 게시글 신청 폼")
    public ResponseEntity<ExpApplicationPageDto> getExpAppPage(@PathVariable Long postId){
        ExpApplicationPageDto response = expApplicationService.getExpAppPage(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}/application")
    @Operation(summary = "해당 체험 게시글 신청 요청")
    public ResponseEntity<ExpApplicationRequest> requestExpApp(
            @PathVariable Long postId,
            @RequestBody ExpApplicationRequest req,
            @AuthenticationPrincipal User applicant
    ) throws Exception {
        ExpApplicationRequest response = expApplicationService.requestExpApp(postId, req, applicant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
