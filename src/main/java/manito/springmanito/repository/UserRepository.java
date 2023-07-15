package manito.springmanito.repository;

import manito.springmanito.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Optional<String> findIdByUserId(String userId);
}
