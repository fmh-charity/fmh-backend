package ru.iteco.fmh.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.employee.ScheduleType;
import ru.iteco.fmh.validation.age.Age;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Информация для регистрации сотрудника")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmployeeRegistrationRequest {
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

    @Pattern(regexp = "[A-Za-z\\d.!_%-+]+@[A-Za-z\\d.-]+\\.[A-Za-z]{2,4}",
            message = "Допустимы заглавные и строчные буквы латинского алфавита и цифры без пробелов")
    @Size(max = 45, message = "Допустимая длина до 45 символов")
    @Schema(name = "email", description = "Адрес электронной почты сотрудника")
    String email;

    @Age(min = 18, max = 100, message = "Возраст должен быть больше 18 и меньше 100 лет")
    @Schema(name = "dateOfBirth", description = "День рождения сотрудника")
    LocalDate dateOfBirth;

    @NonNull
    @Schema(name = "positionId", description = "id должности")
    Integer positionId;

    @Schema(name = "description", description = "Описание сотрудника")
    String description;

    @Schema(name = "scheduleType", description = "Тип графика используемый для автозаполнения")
    ScheduleType scheduleType;

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
