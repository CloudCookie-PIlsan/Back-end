package manito.springmanito.repository;

import manito.springmanito.entity.Manito;
import manito.springmanito.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManitoRepository extends JpaRepository<Manito, Long> {
    List<Manito> findAll();

//    @Modifying
//    @Query(value = "UPDATE Manito m SET m.receiver = :receiver WHERE m.sender = :sender")
//    void updateReceiver(@Param("sender") User sender, @Param("receiver") User receiver);

    @Query("SELECT m.manitoReceiver FROM Manito m WHERE m.manitoSender.userId = :userId")
    User findMyManito(@Param("userId") String userId);

}