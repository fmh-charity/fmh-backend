package ru.iteco.fmh.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Общая информация по пользователю")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class UserShortInfoDto {

    @Schema(name = "id", description = "Идентификатор пользователя")
    Integer id;

    @Schema(name = "firstName", description = "Имя")
    String firstName;

    @Schema(name = "lastName", description = "Фамилия")
    String lastName;

    @Schema(name = "middleName", description = "Отчество")
    String middleName;

    @Schema(name = "isAdmin", description = "Признак администратора")
    boolean isAdmin;

    @Schema(name = "roles", description = "Множество ролей")
    Set<String> roles;

    @Schema(name = "email", description = "Электронная почта")
    UserEmailDto email;

    @Schema(name = "isConfirmed", description = "Статус пользователя")
    Boolean isConfirmed;
}