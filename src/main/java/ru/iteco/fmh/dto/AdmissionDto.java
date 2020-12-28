package ru.iteco.fmh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@ApiModel(description = "информация по госпитализации")
@Builder
@Data
public class AdmissionDto {
    @ApiModelProperty("дата поступления")
    private LocalDate dateIn;
    @ApiModelProperty("дата выписки")
    private LocalDate dateOut;
    @ApiModelProperty(value = "фактическое поступление, признак того, что пациент находится в хосписе")
    private Boolean factIn;
}
