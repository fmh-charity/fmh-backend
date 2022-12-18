package ru.iteco.fmh.dto.patient;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

//форма для respons'a «Пациенты» (Просмотр списка пациентов)
@Schema(description = "Пациент + госпитализации")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class PatientAdmissionDto {

    @Schema(name = "id", description = "id пациента")
    Integer id;

    @Schema(name = "firstName", description = "Имя пациента", pattern = "^[а-яА-ЯёЁa-zA-Z]+$")
    String firstName;

    @Schema(name = "lastName", description = "Фамилия пациента", pattern = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    String lastName;

    @Schema(name = "middleName", description = "Отчество пациента", pattern = "^[а-яА-ЯёЁa-zA-Z-]+$")
    String middleName;

    @Schema(name = "birthday", description = "Дата рождения пациента")
    LocalDate birthday;

    @Schema(name = "patientStatus", description = "Статус госпитализации")
    PatientStatus patientStatus;

    @Schema(name = "dateIn", description = "Фактическая/плановая дата поступления")
    LocalDate dateIn;

    @Schema(name = "dateOut", description = "Фактическая/плановая дата выписки")
    LocalDate dateOut;

    @Schema(name = "dateInBoolean", description = "Признак фактической даты поступления")
    boolean dateInBoolean;

    @Schema(name = "dateOutBoolean", description = "Признак для даты выписки")
    boolean dateOutBoolean;

    // данные для формирования dateIn, dateOut
    @JsonIgnore
    private Long planDateIn;
    @JsonIgnore
    private Long planDateOut;
    @JsonIgnore
    private Long factDateIn;
    @JsonIgnore
    private Long factDateOut;

    @Schema(name = "status", description = "Статус госпитализации")
    PatientStatus status;

    @Schema(name = "room", description = "Информация о палате")
    RoomDtoRs room;
}