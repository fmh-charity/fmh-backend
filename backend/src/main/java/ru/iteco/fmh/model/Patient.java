package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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

    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+$")
    String firstName;

    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    String lastName;

    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z-]+$")
    String middleName;

    LocalDate birthDate;
    boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    Room room;

    //Планируемая дата поступления
    LocalDate planDateIn;
    //Планируемая дата выписки
    LocalDate planDateOut;
    //Фактическая дата поступления
    LocalDate factDateIn;
    //Фактическая дата выписки
    LocalDate factDateOut;

    // Статус пациента
    @Enumerated(EnumType.STRING)
    PatientStatus status = PatientStatus.EXPECTED;
}
