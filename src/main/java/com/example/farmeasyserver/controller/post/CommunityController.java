package com.example.farmeasyserver.controller.post;

import com.example.farmeasyserver.dto.post.community.CommunityRequest;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "Community Post Controller", tags = "CommunityPost")
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
    private final PostService postService;

    @GetMapping
    public Response readCommunityPostList(Pageable pageable){
        return Response.success(postService.getCommunityPostList(pageable));
    }

    @PostMapping("/post")
    public Response create(@Valid @ModelAttribute CommunityRequest req) throws ChangeSetPersister.NotFoundException {
        return Response.success(postService.createCommunityPost(req));
    }

    @ApiOperation(value = "커뮤니티 게시글 조회", notes = "게시글을 조회한다.")
    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) throws ChangeSetPersister.NotFoundException {
        return Response.success(postService.readCommunityPost(postId));
    }

}
