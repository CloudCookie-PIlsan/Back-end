package manito.springmanito.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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


    public User(String username, String userId, String encodePassword) {
        this.username = username;
        this.userId = userId;
        this.password = encodePassword;
    }
}
