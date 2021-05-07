package ru.iteco.fmh.dto.admission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@ApiModel(description = "краткая информация по госпитализации")
@Builder
@Data
public class AdmissionShortInfoDto {
    @ApiModelProperty("идентификатор госпитализации")
    private Integer id;

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
//    @ApiModelProperty(value = "фактическое поступление, признак того, что пациент находится в хосписе")
//    private Boolean factIn;
}
