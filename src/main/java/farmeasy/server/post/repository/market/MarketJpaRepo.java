package farmeasy.server.post.repository.market;

import farmeasy.server.post.domain.market.MarketPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MarketJpaRepo extends JpaRepository<MarketPost,Long> {

    @Query("select mp from MarketPost mp join fetch mp.author join fetch mp.author.farm where mp.id = :id")
    Optional<MarketPost> findByIdWithUser(Long id);

}
