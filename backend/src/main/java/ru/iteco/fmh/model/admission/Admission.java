package ru.iteco.fmh.model.admission;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;

import javax.persistence.*;
import java.time.Instant;


/**
 * Госпитализация
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "admission")
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    //Планируемая дата поступления
    Instant planDateIn;
    //Планируемая дата выписки
    Instant planDateOut;
    //Фактическая дата поступления
    Instant factDateIn;
    //Фактическая дата выписки
    Instant factDateOut;

    // Статус госпитализации
    @Enumerated(EnumType.STRING)
    AdmissionsStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    Room room;

    //Комментарий
    String comment;
    boolean deleted;
}
