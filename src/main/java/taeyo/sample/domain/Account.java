package taeyo.sample.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Account {

    public enum Role {
        ADMIN, USER
    }

    @Id @GeneratedValue
    private int id;

    @Column(unique = true)
    private String username;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Role getRole() {
        return role;
    }
}
