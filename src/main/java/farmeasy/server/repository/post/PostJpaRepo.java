package farmeasy.server.repository.post;

import farmeasy.server.dto.ImageDto;
import farmeasy.server.entity.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    @Query("select new farmeasy.server.dto.ImageDto(i.id,i.post.id,i.originName,i.uniqueName)"+
            " from Image i"+
            " where i.post.id in :postIds")
    List<ImageDto> findImagesDtoByPostIds(List<Long> postIds);

}
