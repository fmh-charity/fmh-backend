package ru.iteco.fmh.dto.patient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.task.Status;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Основная информация по пациенту")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class PatientInfoDto {

    @Schema(name = "firstName", description = "Имя пациента")
    String firstName;

    @Schema(name = "lastName", description = "Фамилия пациента")
    String lastName;

    @Schema(name = "middleName", description = "Отчество пациента")
    String middleName;

    @Schema(name = "birthDate", description = "Дата рождения пациента")
    LocalDate birthDate;

    @Schema(name = "dateIn", description = "Дата поступления")
    LocalDate dateIn;

    @Schema(name = "dateOut", description = "Дата выписки")
    LocalDate dateOut;

    @Schema(name = "status", description = "Имя енама")
    Status status;

    @Schema(name = "chamberName", description = "Имя палаты")
    String chamberName;
}
