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
    @ApiModelProperty("идентификатор госпитализации")
    private Integer id;
    @ApiModelProperty("дата поступления")
    private LocalDate dateIn;
    @ApiModelProperty("дата выписки")
    private LocalDate dateOut;
    @ApiModelProperty(value = "фактическое поступление")
    private Boolean factIn;
    @ApiModelProperty(value = "фактическая выписка")
    private Boolean factOut;
    @ApiModelProperty(value = "комментарий")
    private String comment;
}
