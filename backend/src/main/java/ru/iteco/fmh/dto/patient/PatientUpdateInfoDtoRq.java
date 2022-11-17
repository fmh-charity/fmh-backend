package ru.iteco.fmh.dto.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@ApiModel(description = "основная информация по пациенту для запроса по редактированию")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientUpdateInfoDtoRq {
    @NotBlank
    @ApiModelProperty("имя пациента")
    @Pattern(regexp="^[А-Яа-я]*$")
    private String firstName;
    @ApiModelProperty("фамилия пациента")
    @NotBlank
    @Pattern(regexp="^[А-Яа-я-]*$")
    private String lastName;
    @Pattern(regexp="^[А-Яа-я]*$")
    @ApiModelProperty("отчество пациента")
    private String middleName;
    @NotNull
    @ApiModelProperty("дата рождения пациента")
    private LocalDate birthDate;
}
