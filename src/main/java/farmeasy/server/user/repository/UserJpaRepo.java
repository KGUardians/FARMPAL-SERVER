package farmeasy.server.user.repository;

import farmeasy.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserJpaRepo extends JpaRepository<User,Long> {

    @Query("select u from User u where u.username = :username")
    Optional<User> findByUsername(String username);

}
