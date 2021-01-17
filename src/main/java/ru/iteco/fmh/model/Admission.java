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
    Long id;

    @ManyToOne
    Patient patient;

    //Дата поступления
    LocalDate dateFrom;
    //Дата выписки
    LocalDate dateTo;
    //Фактически поступил
    Boolean factIn;
    //Фактически выписан
    Boolean factOut;
    //Комментарии
    String comment;
}
