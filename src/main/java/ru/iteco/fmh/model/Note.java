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

    //many note can have one patient
    @ManyToOne
    Patient patient;
    String description;

    //many note can have one user
    @ManyToOne
    User creator;
    //many note can have one user
    @ManyToOne
    User executor;

    LocalDateTime createDate;
    LocalDateTime planExecuteDate;
    LocalDateTime factExecuteDate;

    //many note can have one status
    @ManyToOne
    Status status;

    String comment;
    boolean deleted;

}
