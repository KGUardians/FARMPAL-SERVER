package farmeasy.server.repository.post;

import farmeasy.server.entity.board.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByPostIdAndUserId(Long postId, Long userId);
}
