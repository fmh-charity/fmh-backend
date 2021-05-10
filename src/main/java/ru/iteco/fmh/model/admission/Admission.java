package ru.iteco.fmh.model.admission;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;

import javax.persistence.*;
import java.time.LocalDate;


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

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    //Планируемая дата поступления
    LocalDate planDateIn;
    //Планируемая дата выписки
    LocalDate planDateOut;
    //Фактическая  дата поступления
    LocalDate factDateIn;
    //Фактическая дата выписки
    LocalDate factDateOut;

    // Статус госпитализации
    @Enumerated(EnumType.STRING)
    AdmissionsStatus status;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    //Комментарий
    String comment;
    boolean deleted;
}
