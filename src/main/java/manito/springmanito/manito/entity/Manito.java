package manito.springmanito.manito.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import manito.springmanito.global.entity.Timestamped;
import manito.springmanito.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Manito extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manito_id")
    private Long id;

    private boolean isAnswered = false;

    @ManyToOne
    @JoinColumn(name = "manitoSender_id")
    private User manitoSender;

    @ManyToOne
    @JoinColumn(name = "manitoReceiver_id")
    private User manitoReceiver;

    public Manito(User sender, User receiver) {
        this.manitoSender = sender;
        this.manitoReceiver = receiver;
    }

    public void isAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }
}
