package ru.iteco.fmh.dto.patient;

import static lombok.AccessLevel.PRIVATE;

import ru.iteco.fmh.dto.admission.AdmissionDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Schema(description = "Основная информация по пациенту")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class PatientDto {

    @Schema(name = "id", description = "Идентификатор пациента")
    Integer id;

    @Schema(name = "firstName", description = "Имя пациента")
    String firstName;

    @Schema(name = "lastName", description = "Фамилия пациента")
    String lastName;

    @Schema(name = "middleName", description = "Отчество пациента")
    String middleName;

    @Schema(name = "birthDate", description = "Дата рождения пациента")
    Long birthDate;

    @Schema(name = "deleted", description = "Текущая госпитализация")
    boolean deleted;

    @Schema(name = "currentAdmission", description = "Текущая госпитализация")
    AdmissionDto currentAdmission;

    @Schema(name = "admissions", description = "Идентификаторы всех госпитализаций")
    Set<Integer> admissions = new HashSet<>();
}