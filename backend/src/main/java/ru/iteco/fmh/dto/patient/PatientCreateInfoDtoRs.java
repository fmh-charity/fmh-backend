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

@Schema(description = "Основная информация для создания пациента")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class PatientCreateInfoDtoRs {

    @Schema(name = "id", description = "id пациента")
    Integer id;

    @Schema(name = "firstName", description = "Имя пациента", pattern = "^[а-яА-ЯёЁa-zA-Z]+$")
    String firstName;

    @Schema(name = "lastName", description = "Фамилия пациента", pattern = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    String lastName;

    @Schema(name = "middleName", description = "Отчество пациента", pattern = "^[а-яА-ЯёЁa-zA-Z-]+$")
    String middleName;

    @Schema(name = "birthDate", description = "Дата рождения пациента")
    LocalDate birthDate;

    @Schema(name = "dateIn", description = "Фактическая/плановая дата поступления")
    LocalDate dateIn;

    @Schema(name = "dateOut", description = "Фактическая/плановая дата выписки")
    LocalDate dateOut;

    @Schema(name = "dateInBoolean", description = "Признак фактической даты поступления")
    boolean dateInBoolean;

    @Schema(name = "dateOutBoolean", description = "Признак для даты выписки")
    boolean dateOutBoolean;

    @Schema(name = "status", description = "Статус госпитализации")
    PatientStatus status;

    @Schema(name = "room", description = "Информация о палате")
    RoomDtoRs room;
}