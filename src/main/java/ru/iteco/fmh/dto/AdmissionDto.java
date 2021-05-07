package ru.iteco.fmh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import ru.iteco.fmh.dto.patient.PatientDto;

import java.time.LocalDate;

@ApiModel(description = "информация по госпитализации")
@Builder
@Data
public class AdmissionDto {
    @ApiModelProperty("идентификатор госпитализации")
    private Integer id;
    @ApiModelProperty("пациент")
    private PatientDto patient;

    //для сценария "Запланировать госпитализацию пациента"
    @ApiModelProperty("плановая дата поступления")
    private LocalDate planDateIn;
    @ApiModelProperty("плановая дата выписки")
    private LocalDate planDateOut;

    //для сценария "Госпитализация пациента"
    @ApiModelProperty(value = "фактическая дата поступления")
    private LocalDate factDateIn;
    @ApiModelProperty(value = "фактическая дата выписки")
    private LocalDate factDateOut;

    @ApiModelProperty(value = "комментарий")
    private String comment;
}
