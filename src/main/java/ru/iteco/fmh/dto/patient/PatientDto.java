package ru.iteco.fmh.dto.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel(description = "основная информация по пациенту")
@Builder
@AllArgsConstructor//generates a constructor with 1 parameter for each field in your class
@NoArgsConstructor//will generate a constructor with no parameters
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


    public PatientDto(String firstName, String lastName, String middleName, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
    }
}
