package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.community.CommunityListDto;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.community.CreateCommPostRequest;
import com.example.farmeasyserver.dto.post.community.UpdateCommPostReq;
import com.example.farmeasyserver.dto.post.community.comment.CommentDto;
import com.example.farmeasyserver.dto.post.community.comment.CommentRequest;
import com.example.farmeasyserver.entity.board.community.Comment;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.community.CommentJpaRepo;
import com.example.farmeasyserver.repository.post.community.CommunityFilter;
import com.example.farmeasyserver.repository.post.community.CommunityJpaRepo;
import com.example.farmeasyserver.repository.post.community.CommunityRepo;
import com.example.farmeasyserver.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityJpaRepo communityJpaRepo;
    private final CommunityRepo communityRepo;
    private final CommentJpaRepo commentJpaRepo;
    private final PostServiceImpl postService;

    @Override
    public List<CommunityListDto> getRecentCommunityPostDtos() {
        List<CommunityPost> recentCommunityPosts = communityJpaRepo.findTop5OrderByIdDesc();
        List<CommunityListDto> list = convertCommunityPostsToDtos(recentCommunityPosts);
        commentMapping(list);
        return list;
    }

    @Override
    @Transactional
    public CreatePostResponse createCommunityPost(CreateCommPostRequest req, User author) {
        CommunityPost communityPost = postService.createPost(CreateCommPostRequest.toEntity(req.getCommunityType()),req, author);
        communityJpaRepo.save(communityPost);
        return new CreatePostResponse(communityPost.getId(),communityPost.getPostType());
    }

    @Override
    @Transactional
    public Long deleteCommunityPost(Long postId, User author) {
        CommunityPost post = getCommunityPost(postId);
        postService.deletePost(post,author);
        return postId;
    }

    @Override
    public CommunityPostDto readCommunityPost(Long postId){
        return CommunityPostDto.toDto(getCommunityPost(postId));
    }


    @Override
    public CommunityPostDto updateCommunityPost(Long postId, UpdateCommPostReq req, User author) {
        CommunityPost post = getCommunityPost(postId);
        postService.updatePost(author, post, req);
        post.setCommunityType(req.getType());
        communityJpaRepo.save(post);
        return CommunityPostDto.toDto(post);
    }

    @Override
    public Slice<CommunityListDto> getCommunityPosts(CommunityFilter filter, Pageable pageable) {
        Slice<CommunityListDto> listResponse = communityRepo.findPostList(filter,pageable);
        postService.imageMapping(listResponse.stream().toList()); return listResponse;
    }

    private List<CommunityListDto> convertCommunityPostsToDtos(List<CommunityPost> recentCommunityPosts){
        return recentCommunityPosts.stream()
                .map(CommunityListDto::toDto)
                .toList();
    }

    /*

    커뮤니티 게시글 댓글 작성

    */
    @Override
    @Transactional
    public CommentRequest requestComment(Long postId, CommentRequest req, User author) {
        CommunityPost post = getCommunityPost(postId);
        Comment comment = new Comment(req.getComment(),post,author);
        commentJpaRepo.save(comment);
        return req;
    }

    private void commentMapping(List<CommunityListDto> mainPageDto){
        List<Long> postIds = postService.extractPostIds(mainPageDto);
        List<CommentDto> comments = fetchCommentDtoByPostIds(postIds);
        mapCommentCountToPostList(mainPageDto, comments);
    }

    private List<CommentDto> fetchCommentDtoByPostIds(List<Long> postIds){
        return commentJpaRepo.findCommentDtoListByPostIds(postIds);
    }

    private void mapCommentCountToPostList(List<CommunityListDto> postListDto, List<CommentDto> postCommentList){
        Map<Long, List<CommentDto>> imagesByPostId = groupCommentByPostId(postCommentList);
        postListDto.forEach(p -> {
            List<CommentDto> CommentDtoList = imagesByPostId.get(p.getPostId());
            if (CommentDtoList != null && !CommentDtoList.isEmpty()) {
                p.setCommentCount(CommentDtoList.size());
            }
        });
    }

    private Map<Long, List<CommentDto>> groupCommentByPostId(List<CommentDto> postCommentList) {
        return postCommentList.stream()
                .collect(Collectors.groupingBy(CommentDto::getPostId));
    }

    private CommunityPost getCommunityPost(Long postId){
        return communityJpaRepo.findByIdWithUser(postId).orElseThrow(()-> new ResourceNotFoundException("CommunityPost", "communityPost", null));
    }
}
