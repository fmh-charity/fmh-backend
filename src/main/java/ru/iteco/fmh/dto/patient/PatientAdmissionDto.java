package ru.iteco.fmh.dto.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.admission.AdmissionShortInfoDto;

import java.time.LocalDate;
import java.util.List;

//форма для respons'a «Пациенты» (Просмотр списка пациентов)
@ApiModel(description = "пациент + госпитализации")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PatientAdmissionDto {

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
    //

    @ApiModelProperty("дата поступления")
    private LocalDate dateIn;
    @ApiModelProperty("дата выписки")
    private LocalDate dateOut;

    private boolean isPlanDateIn;
    private boolean isPlanDateOut;




}
