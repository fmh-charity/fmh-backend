package ru.iteco.fmh.dto.patient;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

//форма для respons'a «Пациенты» (Просмотр списка пациентов)
@ApiModel(description = "пациент + госпитализации")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PatientAdmissionDto {
    @ApiModelProperty("id пациента")
    private Integer id;
    @ApiModelProperty("имя пациента")
    private String firstName;
    @ApiModelProperty("фамилие пациента")
    private String lastName;
    @ApiModelProperty("отчество пациента")
    private String middleName;
    @ApiModelProperty("дата рождения пациента")
    private LocalDate birthday;

    @ApiModelProperty(value = "статус госпитализации")
    private AdmissionsStatus admissionsStatus;
    @ApiModelProperty("фактическая/плановая дата поступления")
    private LocalDateTime dateIn;
    @ApiModelProperty("фактическая/плановая дата выписки")
    private LocalDateTime dateOut;
    @ApiModelProperty("признак для даты поступления")
    private boolean dateInBoolean;
    @ApiModelProperty("признак для даты выписки")
    private boolean dateOutBoolean;

    // данные для формирования dateIn, dateOut
    @JsonIgnore
    private LocalDateTime planDateIn;
    @JsonIgnore
    private LocalDateTime planDateOut;
    @JsonIgnore
    private LocalDateTime factDateIn;
    @JsonIgnore
    private LocalDateTime factDateOut;
}
