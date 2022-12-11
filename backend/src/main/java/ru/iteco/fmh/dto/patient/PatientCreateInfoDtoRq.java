package ru.iteco.fmh.dto.patient;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Schema(description = "Основная информация для создания пациента")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class PatientCreateInfoDtoRq {

    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+$")
    @Schema(name = "firstName", description = "Имя пациента")
    String firstName;

    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    @Schema(name = "lastName", description = "Фамилия пациента")
    String lastName;

    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z-]+$")
    @Schema(name = "middleName", description = "Отчество пациента")
    String middleName;

    @Schema(name = "birthDate", description = "Дата рождения пациента")
    LocalDate birthDate;
}