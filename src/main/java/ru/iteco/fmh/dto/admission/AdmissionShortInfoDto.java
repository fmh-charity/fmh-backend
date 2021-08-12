package ru.iteco.fmh.dto.admission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(description = "краткая информация по госпитализации")
@Builder
@Data
public class AdmissionShortInfoDto {
    @ApiModelProperty("идентификатор госпитализации")
    private Integer id;

    //для сценария "Запланировать госпитализацию пациента"
    @ApiModelProperty("плановая дата поступления")
    private LocalDateTime planDateIn;
    @ApiModelProperty("плановая дата выписки")
    private LocalDateTime planDateOut;

    //для сценария "Госпитализация пациента"
    @ApiModelProperty(value = "фактическая дата поступления")
    private LocalDateTime factDateIn;
    @ApiModelProperty(value = "фактическая дата выписки")
    private LocalDateTime factDateOut;
//    @ApiModelProperty(value = "фактическое поступление, признак того, что пациент находится в хосписе")
//    private Boolean factIn;
}
