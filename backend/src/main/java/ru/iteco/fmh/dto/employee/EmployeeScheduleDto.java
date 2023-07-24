package ru.iteco.fmh.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.iteco.fmh.model.employee.EmploymentType;
import ru.iteco.fmh.model.employee.ScheduleType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@Data
public class EmployeeScheduleDto {

    private final int sheduleId;
    private final LocalDate date;
    private final LocalTime timeStartWork;
    private final LocalTime timeEndWork;
    private final EmploymentType employmentType;
}
