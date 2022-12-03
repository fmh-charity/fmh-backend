package ru.iteco.fmh.model.task.claim;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.Instant;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "claim_comment")
public class ClaimComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "claim_id")
    Claim claim;
    String description;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;
    Instant createDate;
}
