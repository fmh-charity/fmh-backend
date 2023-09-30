package ru.iteco.fmh.model.employee;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.Profile;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    Profile profile;

    @ManyToOne
    @JoinColumn(name = "positions_id")
    Position position;

    @Column(name = "description")
    String description;

    @Column(name = "active")
    Boolean active;

    @Column(name = "schedule_type")
    @Enumerated(EnumType.STRING)
    ScheduleType scheduleType;

    @Column(name = "work_start_time")
    LocalTime workStartTime;

    @Column(name = "work_end_time")
    LocalTime workEndTime;

    @Column(name = "schedule_start_date")
    LocalDate scheduleStartDate;

    @OneToMany(mappedBy = "employee")
    List<Schedule> scheduleList;
}
