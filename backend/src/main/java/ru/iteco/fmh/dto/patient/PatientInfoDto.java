package ru.iteco.fmh.dto.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "основная информация по пациенту")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientInfoDto {

    @ApiModelProperty("id пациента")
    private Integer id;
    @ApiModelProperty("фамилия пациента")
    private String lastName;
    @ApiModelProperty("имя пациента")
    private String firstName;
    @ApiModelProperty("отчество пациента")
    private String middleName;
    @ApiModelProperty("дата рождения пациента")
    private LocalDate birthDate;
    @ApiModelProperty("дата поступления пациента")
    private LocalDate dateIn;
    @ApiModelProperty("дата выписки пациента")
    private LocalDate dateOut;
    @ApiModelProperty("имя палаты")
    private String chamberName;

    @ApiModelProperty("текущая госпитализация")
    private boolean deleted;
    @ApiModelProperty("текущая госпитализация")
    private AdmissionDto currentAdmission;
    @ApiModelProperty("id всех госпитализаций")
    private Set<Integer> admissions = new HashSet<>();


}
