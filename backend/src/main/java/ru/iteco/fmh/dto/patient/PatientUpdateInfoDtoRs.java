package ru.iteco.fmh.dto.patient;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Schema(description = "Основная информация по пациенту для возврата отредактированного пациента")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class PatientUpdateInfoDtoRs {

    @Schema(name = "id", description = "id пациента")
    Integer id;

    @Schema(name = "firstName", description = "Имя пациента")
    String firstName;

    @Schema(name = "lastName", description = "Фамилия пациента")
    String lastName;

    @Schema(name = "middleName", description = "Отчество пациента")
    String middleName;

    @Schema(name = "birthDate", description = "Дата рождения пациента")
    LocalDate birthDate;
}