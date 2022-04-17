package ru.iteco.fmh.model.admission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    //Комментарий
    String comment;
    boolean deleted;
}
