package farmeasy.server.post.repository.community;

import farmeasy.server.post.domain.community.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunityJpaRepo extends JpaRepository<CommunityPost,Long> {
    @Query("SELECT cp FROM CommunityPost cp " +
            "ORDER BY cp.id DESC limit 5")
    List<CommunityPost> findTop5OrderByIdDesc();

    @Query("select cp from CommunityPost cp join fetch cp.author where cp.id = :postId")
    Optional<CommunityPost> findByIdWithUser(Long postId);

}
