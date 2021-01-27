package ru.iteco.fmh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel(description = "основная информация по пациенту")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDto {
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
    @ApiModelProperty("ФИО пациента, в формате \"Кузнецова Н.П.\"")
    private String shortPatientName;

}
