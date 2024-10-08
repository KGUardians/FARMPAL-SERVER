package farmeasy.server.user.repository;

import farmeasy.server.farm.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmJpaRepo extends JpaRepository<Farm,Long> {
}
