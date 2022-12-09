package ru.iteco.fmh.dto.patient;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Schema(name = "Основная информация для создания пациента")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class PatientCreateInfoDtoRs {

    @Schema(name = "id", description = "id пациента")
    Integer id;

    @Schema(name = "firstName", description = "имя пациента")
    String firstName;

    @Schema(name = "lastName", description = "фамилия пациента")
    String lastName;

    @Schema(name = "middleName", description = "отчество пациента")
    String middleName;

    @Schema(name = "birthDate", description = "дата рождения пациента")
    LocalDate birthDate;
}