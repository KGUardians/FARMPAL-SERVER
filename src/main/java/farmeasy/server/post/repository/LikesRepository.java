package farmeasy.server.post.repository;

import farmeasy.server.post.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByPostIdAndUserId(Long postId, Long userId);
}
