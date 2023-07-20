package manito.springmanito.manito.repository;

import manito.springmanito.manito.entity.Manito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface ManitoRepository extends JpaRepository<Manito, Long> {
    /**
     * 오늘의 마니또 가져오기
     * @param userid
     * @return
     */
    @Query("SELECT m FROM Manito m WHERE m.manitoSender.userId = :userid AND DATE(m.createdAt) = :today")
    Optional<Manito> findManitoByGiverNameAndToday(@Param("userid") String userid, @Param("today") Date today);

    /**
     * 어제의 마니또 가져오기
     * @param yesterday
     * @return
     */
    @Query("SELECT m FROM Manito m WHERE m.manitoReceiver.userId = :userId AND DATE(m.createdAt) = :yesterday")
    Optional<Manito> findManitoByReceiverIdAndYesterday(@Param("userId") String userId, @Param("yesterday") Date yesterday);

    /**
     * 오늘 나를 마니또해주는사람 가져오기
     * @param userId
     * @return
     */
    @Query("SELECT m FROM Manito m WHERE m.manitoReceiver.userId = :userId AND DATE(m.createdAt) = :today")
    Optional<Manito> findManitoByReceiverIdAndToday(@Param("userId") String userId,  @Param("today") Date today);

}