package farmeasy.server.post.repository;

import farmeasy.server.file.dto.ImageDto;
import farmeasy.server.post.domain.Post;
import farmeasy.server.post.domain.community.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    @Query("select new farmeasy.server.file.dto.ImageDto(i.id,i.post.id,i.originName,i.uniqueName)"+
            " from Image i"+
            " where i.post.id in :postIds")
    List<ImageDto> findImagesDtoByPostIds(List<Long> postIds);
}
