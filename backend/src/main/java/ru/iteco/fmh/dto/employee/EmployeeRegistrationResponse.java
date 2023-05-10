package ru.iteco.fmh.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.employee.ScheduleType;

import java.time.LocalDate;
import java.time.LocalTime;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Информация для регистрации сотрудника")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmployeeRegistrationResponse {

    @Schema(name = "employeeId", description = "id сотрудника")
    Integer employeeId;

    @Schema(name = "lastName", description = "Фамилия сотрудника")
    String lastName;

    @Schema(name = "firstName", description = "Имя сотрудника")
    String firstName;

    @Schema(name = "middleName", description = "Отчуство сотрудника")
    String middleName;

    @Schema(name = "email", description = "Электронная почта сотрудника")
    String email;

    @Schema(name = "dateOfBirth", description = "День рождения сотрудника")
    LocalDate dateOfBirth;

    @Schema(name = "position", description = "Должность")
    String position;

    @Schema(name = "description", description = "Описание сотрудника")
    String description;

    @Schema(name = "isActive", description = "Активность сотрудника (не уволен)")
    boolean isActive;

    @Schema(name = "scheduleType", description = "Тип графика используемый для автозаполнения")
    ScheduleType scheduleType;

    @Schema(name = "scheduleStartDate",
            description = "Дата отсчета для скользящих графиков работы (для автозаполнения графика)")
    LocalDate scheduleStartDate;

    @Schema(name = "workStartTime", description = "Время начала работы по графику (для автозаполнения графика)")
    LocalTime workStartTime;

    @Schema(name = "workEndTime", description = "Время окончания работы по графику (для автозаполнения графика)")
    LocalTime workEndTime;

}
