package manito.springmanito.user.repository;

import manito.springmanito.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 유저 아이디로 유저 찾기
     * @param userId
     * @return
     */
    Optional<User> findByUserId(String userId);
}
