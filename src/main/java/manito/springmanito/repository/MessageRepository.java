package manito.springmanito.repository;

import manito.springmanito.entity.Message;
import manito.springmanito.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByMessageGiver(User messageGiver);
    List<Message> findByMessageReceiver(User messageReceiver);
}
