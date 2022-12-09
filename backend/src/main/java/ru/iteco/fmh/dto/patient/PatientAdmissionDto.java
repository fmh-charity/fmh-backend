package ru.iteco.fmh.dto.patient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.iteco.fmh.model.PatientStatus;

import java.time.LocalDate;

//форма для respons'a «Пациенты» (Просмотр списка пациентов)
@ApiModel(description = "пациент + госпитализации")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class PatientAdmissionDto extends PatientDto {
    @ApiModelProperty(value = "статус госпитализации")
    private PatientStatus patientStatus;
    @ApiModelProperty(value = "идентификатор палаты")
    private Integer roomId;
    @ApiModelProperty("фактическая/плановая дата поступления")
    private Long dateIn;
    @ApiModelProperty("фактическая/плановая дата выписки")
    private Long dateOut;
    @ApiModelProperty("признак для даты поступления")
    private boolean dateInBoolean;
    @ApiModelProperty("признак для даты выписки")
    private boolean dateOutBoolean;

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
