package farmeasy.server.user.repository;

import farmeasy.server.user.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmJpaRepo extends JpaRepository<Farm,Long> {
}
