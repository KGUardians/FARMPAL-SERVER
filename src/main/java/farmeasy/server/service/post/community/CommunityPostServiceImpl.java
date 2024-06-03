package farmeasy.server.service.post.community;

import farmeasy.server.dto.post.CreatePostResponse;
import farmeasy.server.dto.post.community.CommunityListDto;
import farmeasy.server.dto.post.community.CommunityPostDto;
import farmeasy.server.dto.post.community.CreateCommPostRequest;
import farmeasy.server.dto.post.community.UpdateCommPostReq;
import farmeasy.server.dto.post.community.comment.CommentRequest;
import farmeasy.server.entity.board.community.Comment;
import farmeasy.server.entity.board.community.CommunityPost;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.post.community.CommentJpaRepo;
import farmeasy.server.repository.post.community.CommunityFilter;
import farmeasy.server.repository.post.community.CommunityJpaRepo;
import farmeasy.server.repository.post.community.CommunityRepo;
import farmeasy.server.service.file.FileService;
import farmeasy.server.service.post.PostService;
import farmeasy.server.util.PostUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityJpaRepo communityJpaRepo;
    private final CommunityRepo communityRepo;
    private final CommentJpaRepo commentJpaRepo;
    private final PostService postService;
    private final FileService fileService;
    private final PostUtil postUtil;

    @Override
    public List<CommunityListDto> getRecentCommunityPostDtos() {
        List<CommunityPost> recentCommunityPosts = communityJpaRepo.findTop5OrderByIdDesc();
        List<CommunityListDto> list = postUtil.convertCommunityPostsToDtos(recentCommunityPosts);
        postUtil.commentMapping(list);
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
        CommunityPost post = postUtil.getCommunityPost(postId);
        postService.deletePost(post,author);
        return postId;
    }

    @Override
    public CommunityPostDto readCommunityPost(Long postId){
        return CommunityPostDto.toDto(postUtil.getCommunityPost(postId));
    }


    @Override
    public CommunityPostDto updateCommunityPost(Long postId, UpdateCommPostReq req, User author) {
        CommunityPost post = postUtil.getCommunityPost(postId);
        postService.updatePost(author, post, req);
        post.setCommunityType(req.getType());
        communityJpaRepo.save(post);
        return CommunityPostDto.toDto(post);
    }

    @Override
    public Slice<CommunityListDto> getCommunityPosts(CommunityFilter filter, Pageable pageable) {
        Slice<CommunityListDto> listResponse = communityRepo.findPostList(filter,pageable);
        fileService.imageMapping(listResponse.stream().toList()); return listResponse;
    }

    /*

    커뮤니티 게시글 댓글 작성

    */
    @Override
    @Transactional
    public CommentRequest requestComment(Long postId, CommentRequest req, User author) {
        CommunityPost post = postUtil.getCommunityPost(postId);
        Comment comment = new Comment(req.getComment(),post,author);
        commentJpaRepo.save(comment);
        return req;
    }

}
