package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Записка
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    User executor;

    LocalDateTime createDate;
    LocalDateTime planExecuteTime;
    LocalDateTime factExecuteTime;

    @Enumerated(EnumType.STRING)
    StatusE status;

    String comment;
    boolean deleted;
}
