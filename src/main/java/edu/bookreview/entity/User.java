package edu.bookreview.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"username", "password", "nickname"})
@Table(name = "USERS")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;


    @Builder
    public User(String username, String password, String nickname) {
        Assert.hasText(username, "username must not be empty");
        Assert.hasText(password, "password must not be empty");
        Assert.hasText(nickname, "nickname must not be empty");

        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

}