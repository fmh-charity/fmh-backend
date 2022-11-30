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

@ApiModel(description = "основная информация по пациенту для запроса по редактированию")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientUpdateInfoDtoRq {
    @ApiModelProperty("имя пациента")
    @NotBlank
    @Pattern(regexp = "[А-Яа-яЁёa-zA-Z]+")
    private String firstName;
    @ApiModelProperty("фамилия пациента")
    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    private String lastName;
    @NotBlank
    @Pattern(regexp = "[А-Яа-яЁёa-zA-Z/-]+")
    @ApiModelProperty("отчество пациента")
    private String middleName;
    @ApiModelProperty("дата рождения пациента")
    private LocalDate birthDate;
}
