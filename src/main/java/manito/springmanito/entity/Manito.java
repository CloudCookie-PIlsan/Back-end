package manito.springmanito.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Manito extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manito_id")
    private Long id;

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
}
