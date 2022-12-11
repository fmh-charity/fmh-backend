package ru.iteco.fmh.dto.patient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.model.PatientStatus;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

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

    @Schema(name = "deleted", description = "Признак удаления")
    boolean deleted;

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

    @Schema(name = "room", description = "Информация о палате")
    RoomDtoRs room;
}