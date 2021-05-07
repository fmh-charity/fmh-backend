package ru.iteco.fmh.model.user;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Роли пользователя
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "user_role")
public class UserRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    //many userRole can have one user
    @ManyToOne
    User user;
    //many userRole can have one role
    @ManyToOne
    Role rol;
    boolean deleted;
}
