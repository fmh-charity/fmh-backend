package ru.iteco.fmh.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.RoleClaimStatus;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@JsonFormat
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Заявка на роль пользователя, средняя")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class UserRoleClaimDto {

    @Schema(name = "id", description = "Идентификатор пользователя")
    Integer id;

    @Schema(name = "role", description = "Название роли")
    String role;

    @Schema(name = "status", description = "Статус заявки на роль пользователя")
    RoleClaimStatus status;

    @Schema(name = "createdAt", description = "Дата создания заявки на роль")
    Instant createdAt;
}
