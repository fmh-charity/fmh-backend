package ru.iteco.fmh.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Профиль пользователя")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ProfileDtoRs {
    @Schema(name = "lastName", description = "Фамилия")
    String lastName;

    @Schema(name = "firstName", description = "Имя")
    String firstName;

    @Schema(name = "middleName", description = "Отчество")
    String middleName;

    @Schema(name = "email", description = "Электронная почта")
    String email;

    @Schema(name = "dateOfBirth", description = "Дата рождения")
    LocalDate dateOfBirth;

    @Schema(name = "emailConfirmed", description = "Флаг подтверждения email")
    boolean emailConfirmed;
}
