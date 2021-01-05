package ru.iteco.fmh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@ApiModel(description = "основная информация по пациенту")
@Builder
@Data
public class PatientMainInfoDto {
    @ApiModelProperty("id пациента")
    private Integer id;
    @ApiModelProperty("имя пациента")
    private String name;
    @ApiModelProperty("фамилия пациента")
    private String lastName;
    @ApiModelProperty("отчество пациента")
    private String middleName;
    @ApiModelProperty("дата рождения пациента")
    private LocalDate birthDate;
}
