package farmeasy.server.post.service.community;

import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.community.CommunityListDto;
import farmeasy.server.post.dto.community.CommunityPostDto;
import farmeasy.server.post.dto.community.CreateCommPostRequest;
import farmeasy.server.post.dto.community.UpdateCommPostReq;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.community.CommunityFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityPostService {

    List<CommunityListDto> getRecentCommunityPostDtos();

    CreatePostResponse createCommunityPost(CreateCommPostRequest req, User author);

    void deleteCommunityPost(Long postId, User user);

    CommunityPostDto readCommunityPost(Long postId);

    void updateCommunityPost(Long postId, UpdateCommPostReq req, User user);

    Slice<CommunityListDto> getCommunityPosts(CommunityFilter filter, Pageable pageable);
}