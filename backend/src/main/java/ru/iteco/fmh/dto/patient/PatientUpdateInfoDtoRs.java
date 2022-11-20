package ru.iteco.fmh.dto.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel(description = "основная информация по пациенту для возврата отредактированного пациента")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientUpdateInfoDtoRs {
    @ApiModelProperty("id пациента")
    private Integer id;
    @ApiModelProperty("имя пациента")
    private String firstName;
    @ApiModelProperty("фамилия пациента")
    private String lastName;
    @ApiModelProperty("отчество пациента")
    private String middleName;
    @ApiModelProperty("дата рождения пациента")
    private LocalDate birthDate;
}
