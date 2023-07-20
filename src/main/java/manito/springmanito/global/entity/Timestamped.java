package manito.springmanito.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Getter
@MappedSuperclass // 엔티티 클래스의 상속
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {
    // 작성 날짜
    @CreatedDate
    @Column(updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @PrePersist
    public void prePersist() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 9);
        createdAt = calendar.getTime();
    }
}
