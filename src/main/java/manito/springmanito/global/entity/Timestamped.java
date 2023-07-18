package manito.springmanito.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@MappedSuperclass // 엔티티 클래스의 상속
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {
    // 작성 날짜
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;
}
