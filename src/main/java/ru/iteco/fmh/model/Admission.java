package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "admission")
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    Patient patient;

    //Статус госпитализации
    AdmissionsStatus admissionsStatus;
    //Дата поступления
    LocalDate planDateIn;
    //Дата выписки
    LocalDate planDateOut;
    //Фактически поступил
    LocalDate factDateIn;
    //Фактически выписан
    LocalDate factDateOut;
    //Комментарии
    String comment;
}
