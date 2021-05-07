package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
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

    String description;

    //many claim can have one user
    @ManyToOne
    User creator;
    @ManyToOne
    User executor;

    LocalDate createDate;
    LocalDate planExecuteDate;
    LocalDate factExecuteDate;

    @Enumerated(EnumType.STRING)
    StatusE status;

    String comment;
    boolean deleted;
}
