package ru.iteco.fmh.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.employee.ScheduleType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalTime;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Информация по изменению данных о сотруднике")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmployeeChangingRequest {

    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    @Schema(name = "lastName", description = "Фамилия сотрудника")
    String lastName;

    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+$")
    @Schema(name = "firstName", description = "Имя сотрудника")
    String firstName;

    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z-]+$")
    @Schema(name = "middleName", description = "Отчество сотрудника")
    String middleName;

    @Schema(name = "positionId")
    int positionId;

    @Schema(name = "scheduleType")
    ScheduleType scheduleType;

    @Schema(name = "description")
    String description;

    @Schema(name = "scheduleStartDate",
            description = "Дата отсчета для скользящих графиков работы (для автозаполнения графика)")
    LocalDate scheduleStartDate;

    @Schema(name = "workStartTime", description = "Время начала работы по графику (для автозаполнения графика)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime workStartTime;

    @Schema(name = "workEndTime", description = "Время окончания работы по графику (для автозаполнения графика)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime workEndTime;
}
