package ru.iteco.fmh.dto.patient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.PatientStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Schema(description = "Основная информация по пациенту для запроса по редактированию")
public class PatientUpdateInfoDtoRq {

    @NotBlank
    @Pattern(regexp = "[А-Яа-яЁёa-zA-Z]+")
    @Schema(name = "firstName", description = "Имя пациента", pattern = "^[а-яА-ЯёЁa-zA-Z]+$")
    String firstName;

    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    @Schema(name = "lastName", description = "Фамилия пациента", pattern = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    String lastName;

    @NotBlank
    @Pattern(regexp = "[А-Яа-яЁёa-zA-Z/-]+")
    @Schema(name = "middleName", description = "Отчество пациента", pattern = "^[а-яА-ЯёЁa-zA-Z-]+$")
    String middleName;

    @Schema(name = "birthDate", description = "Дата рождения пациента")
    LocalDate birthDate;

    @Schema(name = "dateIn", description = "Фактическая/плановая дата поступления")
    LocalDate dateIn;

    @Schema(name = "dateOut", description = "Фактическая/плановая дата выписки")
    LocalDate dateOut;

    @Schema(name = "dateInBoolean", description = "Признак для даты поступления")
    boolean dateInBoolean;

    @Schema(name = "dateOutBoolean", description = "Признак для даты выписки")
    boolean dateOutBoolean;

    @Schema(name = "status", description = "Статус госпитализации")
    PatientStatus status;

    @Schema(name = "room", description = "Идентификатор палаты")
    Integer roomId;

    @Schema(name = "deleted", description = "Признак удаления")
    boolean deleted;
}