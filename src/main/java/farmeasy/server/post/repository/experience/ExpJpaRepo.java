package farmeasy.server.post.repository.experience;

import farmeasy.server.post.domain.exprience.ExperiencePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExpJpaRepo extends JpaRepository<ExperiencePost,Long> {

    @Query("select ep from ExperiencePost ep " +
            "join fetch ep.author " +
            "join fetch ep.author.farm " +
            "where ep.id = :id")
    Optional<ExperiencePost> findByIdWithUser(Long id);

}
