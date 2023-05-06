package ru.iteco.fmh.model.employee;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "work_start_time")
    LocalTime workStartTime;

    @Column(name = "work_end_time")
    LocalTime workEndTime;

    @Column(name = "employment_type")
    EmploymentType employmentType;

    @Column(name = "week")
    int week;

    @Column(name = "month")
    Month month;

    @Column(name = "year")
    int year;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

}
