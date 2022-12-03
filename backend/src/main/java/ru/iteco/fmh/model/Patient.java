package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

/**
 * Пациент
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String firstName;
    String lastName;
    String middleName;
    Instant birthDate;
    boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    Room room;

    //Планируемая дата поступления
    Instant planDateIn;
    //Планируемая дата выписки
    Instant planDateOut;
    //Фактическая дата поступления
    Instant factDateIn;
    //Фактическая дата выписки
    Instant factDateOut;

    // Статус пациента
    @Enumerated(EnumType.STRING)
    PatientStatus status = PatientStatus.EXPECTED;
}
