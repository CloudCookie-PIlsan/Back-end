package manito.springmanito.manito.repository;

import manito.springmanito.manito.entity.Manito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ManitoRepository extends JpaRepository<Manito, Long> {
    List<Manito> findAll();

    @Query("SELECT m FROM Manito m WHERE m.manitoSender.userId = :userId")
    Optional<Manito> findMyManito(@Param("userId") String userId);

    @Query("SELECT m FROM Manito m WHERE m.manitoSender.userId = :userid AND DATE(m.createdAt) = CURRENT_DATE")
    Optional<Manito> findManitoByGiverNameAndToday(@Param("userid") String userid);

    @Query("SELECT m FROM Manito m WHERE m.manitoReceiver.userId = :userId AND DATE(m.createdAt) = :yesterday")
    Optional<Manito> findManitoByReceiverIdAndYesterday(@Param("userId") String userId, @Param("yesterday") LocalDate yesterday);

    @Query("SELECT m FROM Manito m WHERE m.manitoReceiver.userId = :userId AND DATE(m.createdAt) = CURRENT_DATE")
    Optional<Manito> findManitoByReceiverIdAndToday(@Param("userId") String userId);

}