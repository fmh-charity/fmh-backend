package ru.iteco.fmh.dto.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.admission.AdmissionDto;

import java.util.HashSet;
import java.util.Set;

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
    @ApiModelProperty("фамилия пациента")
    private String lastName;
    @ApiModelProperty("отчество пациента")
    private String middleName;
    @ApiModelProperty("дата рождения пациента")
    private Long birthDate;
    @ApiModelProperty("текущая госпитализация")
    private boolean deleted;
    @ApiModelProperty("текущая госпитализация")
    private AdmissionDto currentAdmission;
    @ApiModelProperty("id всех госпитализаций")
    private Set<Integer> admissions = new HashSet<>();
}