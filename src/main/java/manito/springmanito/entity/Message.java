package manito.springmanito.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Message extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "messageGiver_id")
    private User messageGiver;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "messageReceiver_id")
    private User messageReceiver;

    @Column(nullable = false, length = 500)
    private String content;

}
