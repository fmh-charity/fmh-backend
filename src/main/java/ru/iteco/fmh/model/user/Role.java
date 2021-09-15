package ru.iteco.fmh.model.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Роли
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    RoleE name;
    boolean deleted;
}
