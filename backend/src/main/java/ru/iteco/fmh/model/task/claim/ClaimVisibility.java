package ru.iteco.fmh.model.task.claim;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.Role;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "claimVisibility")
public class ClaimVisibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "claim_id")
    Claim claim;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    Boolean deleted;
}
