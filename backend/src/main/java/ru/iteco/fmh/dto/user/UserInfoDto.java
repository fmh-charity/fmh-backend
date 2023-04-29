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
@Schema(description = "Информация по пользователю")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class UserInfoDto {
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

    @Schema(name = "email", description = "Электронная почта")
    UserEmailDto email;

    @Schema(name = "roles", description = "Множество ролей")
    Set<String> roles;

    @Schema(name = "isConfirmed", description = "Признак подтверждения пользователя")
    boolean isConfirmed;

    @Schema(name = "userRoleClaim", description = "Заявка на роль пользователя, средняя")
    UserRoleClaimDto userRoleClaim;


}
