package ru.iteco.fmh.model.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

/**
 * Пользователь
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String login;
    String password;
    String firstName;
    String lastName;
    String middleName;
    String phoneNumber;
    String email;
    boolean deleted;
}
