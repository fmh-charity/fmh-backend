package ru.iteco.fmh.model.claim;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Заявка
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;
    String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    User executor;

    LocalDateTime createDate;
    LocalDateTime planExecuteDate;
    LocalDateTime factExecuteDate;

    @Enumerated(EnumType.STRING)
    StatusE status;

    boolean deleted;
}
