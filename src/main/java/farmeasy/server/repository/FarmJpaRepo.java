package farmeasy.server.repository;

import farmeasy.server.entity.user.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmJpaRepo extends JpaRepository<Farm,Long> {
}
