package ru.iteco.fmh.dto.patient;

import static lombok.AccessLevel.PRIVATE;

import ru.iteco.fmh.model.admission.AdmissionsStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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

    @Schema(name = "firstName", description = "Имя пациента")
    String firstName;

    @Schema(name = "lastName", description = "Фамилия пациента")
    String lastName;

    @Schema(name = "middleName", description = "Отчество пациента")
    String middleName;

    @Schema(name = "birthday", description = "Дата рождения пациента")
    LocalDate birthday;

    @Schema(name = "admissionsStatus", description = "Статус госпитализации")
    AdmissionsStatus admissionsStatus;

    @Schema(name = "dateIn", description = "Фактическая/плановая дата поступления")
    Long dateIn;

    @Schema(name = "dateOut", description = "Фактическая/плановая дата выписки")
    Long dateOut;

    @Schema(name = "dateInBoolean", description = "Признак для даты поступления")
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
}