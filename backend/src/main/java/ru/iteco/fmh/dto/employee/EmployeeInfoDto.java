package ru.iteco.fmh.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.iteco.fmh.model.employee.Schedule;
import ru.iteco.fmh.model.employee.ScheduleType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@AllArgsConstructor
@Builder
@Schema(description = "Информация по cотруднику")
@Data
public class EmployeeInfoDto {

    @Schema(name = "id", description = "Идентификатор сотрудника")
    private final int id;

    @Schema(name = "firstName", description = "Имя")
    private final String firstName;

    @Schema(name = "lastName", description = "Фамилия")
    private final String lastName;

    @Schema(name = "middleName", description = "Отчество")
    private final String middleName;

    @Schema(name = "work_start_time", description = "Фамилия")
    LocalTime workStartTime;

    @Schema(name = "work_end_time", description = "Фамилия")
    LocalTime workEndTime;

    @Schema(name = "schedule_start_date", description = "Фамилия")
    LocalDate scheduleStartDate;

    @Schema(name = "active", description = "активность")
    Boolean active;

    @Column(name = "schedule_type")
    @Enumerated(EnumType.STRING)
    ScheduleType scheduleType;



}
