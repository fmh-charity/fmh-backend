package ru.iteco.fmh.dto.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@ApiModel(description = "основная информация для создания пациента")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientCreateInfoDtoRq {
    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+$")
    @ApiModelProperty("имя пациента")
    private String firstName;

    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    @ApiModelProperty("фамилия пациента")
    private String lastName;

    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z-]+$")
    @ApiModelProperty("отчество пациента")
    private String middleName;

    @ApiModelProperty("дата рождения пациента")
    private LocalDate birthDate;
}

