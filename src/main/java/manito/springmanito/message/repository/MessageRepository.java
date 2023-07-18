package manito.springmanito.message.repository;

import manito.springmanito.message.entity.Message;
import manito.springmanito.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByMessageGiver(User messageGiver);
    List<Message> findByMessageReceiver(User messageReceiver);
}
