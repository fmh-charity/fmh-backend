package ru.iteco.fmh.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.RoleClaimStatus;

import javax.persistence.*;
import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

/**
 * Заявка на роль пользователя - ПОЛНАЯ
 */
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Заявка на роль пользователя")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class UserRoleClaimFull {

    @Schema(name = "id", description = "Идентификатор")
    Integer id;

    @Schema(name = "userId", description = "Идентификатор пользователя")
    Integer userId;
    @Schema(name = "roleId", description = "Идентификатор роли")
    Integer roleId;
    @Schema(name = "status", description = "Статус заявки на роль пользователя")
    RoleClaimStatus status;

    @Schema(name = "createdAt", description = "Время создания")
    Instant createdAt;
    @Schema(name = "updatedAt", description = "Время изменения")
    Instant updatedAt;
}