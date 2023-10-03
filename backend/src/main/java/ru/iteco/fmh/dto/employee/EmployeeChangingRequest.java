package ru.iteco.fmh.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Информация по изменению данных о пользователе")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmployeeChangingRequest {

    @NotBlank()
    @Schema(name = "lastName", description = "Фамилия")
    String lastName;

    @NotBlank()
    @Schema(name = "firstName", description = "Имя")
    String firstName;

    @NotBlank()
    @Schema(name = "middleName", description = "Отчество")
    String middleName;

}
