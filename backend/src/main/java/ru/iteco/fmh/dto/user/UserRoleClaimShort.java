package ru.iteco.fmh.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.RoleClaimStatus;

import javax.persistence.Column;

import static lombok.AccessLevel.PRIVATE;

/**
 * Заявка на роль пользователя - КОРОТКАЯ
 */
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Заявка на роль пользователя")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class UserRoleClaimShort {

    @Schema(name = "userId", description = "Идентификатор пользователя")
    Integer userId;
    @Schema(name = "roleId", description = "Идентификатор роли")
    Integer roleId;
    @Schema(name = "status", description = "Статус заявки на роль пользователя")
    RoleClaimStatus status;

}
