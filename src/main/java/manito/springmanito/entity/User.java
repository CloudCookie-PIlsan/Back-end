package manito.springmanito.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "manitoSender", cascade = CascadeType.REMOVE)
    private List<Manito> manitoSender;

    @OneToMany(mappedBy = "manitoReceiver", cascade = CascadeType.REMOVE)
    private List<Manito> manitoReceiver;

    @OneToMany(mappedBy = "messageGiver", cascade = CascadeType.REMOVE)
    private List<Message> messageGiver;

    @OneToMany(mappedBy = "messageReceiver", cascade = CascadeType.REMOVE)
    private List<Message> messageReceiver;

    public User(String username, String userId, String encodePassword) {
        this.username = username;
        this.userId = userId;
        this.password = encodePassword;
    }

}
