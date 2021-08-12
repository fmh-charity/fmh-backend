package ru.iteco.fmh.model.wish;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * просьба
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "wish")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;
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
