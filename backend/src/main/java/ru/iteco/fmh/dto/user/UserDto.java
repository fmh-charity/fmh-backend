package ru.iteco.fmh.dto.user;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(description = "Информация по пользователю")
@Builder
@Data
@AllArgsConstructor//generates a constructor with 1 parameter for each field in your class
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserDto {

    @Schema(name = "id", description = "Идентификатор пользователя")
    Integer id;

    @Schema(name = "login", description = "Логин")
    String login;

    @Schema(name = "password", description = "Пароль")
    String password;

    @Schema(name = "profile", description = "Профиль пользователя")
    ProfileDtoRs profile;

}