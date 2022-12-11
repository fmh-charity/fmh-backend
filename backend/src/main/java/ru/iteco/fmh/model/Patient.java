package ru.iteco.fmh.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    LocalDate planDateIn;
    LocalDate planDateOut;
    LocalDate factDateIn;
    LocalDate factDateOut;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    PatientStatus status = PatientStatus.EXPECTED;

    @JoinColumn(name = "room_id")
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    Room room;
}
