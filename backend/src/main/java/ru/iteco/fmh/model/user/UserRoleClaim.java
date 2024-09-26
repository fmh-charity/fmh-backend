package ru.iteco.fmh.model.user;


import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

/**
 * Заявка на роль пользователя
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_role_claim")
public class UserRoleClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    RoleClaimStatus status;

    @Column(name = "created_at")
    Instant createdAt;
    @Column(name = "updated_at")
    Instant updatedAt;
}