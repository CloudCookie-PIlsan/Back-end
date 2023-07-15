package manito.springmanito.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Manito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    ;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public Manito(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Manito() {

    }
}
