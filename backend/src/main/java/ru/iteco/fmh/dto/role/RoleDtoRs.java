package ru.iteco.fmh.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Schema(description = "Основная информация по роли в системе")
public class RoleDtoRs {
    @Schema(name = "id", description = "id в системе")
    Integer id;
    @Schema(name = "name", description = "Имя роли")
    String name;
    @Schema(name = "description", description = "Описание документа")
    String description;
    @Schema(name = "needConfirm", description = "Флаг подверждения получения")
    boolean needConfirm;
}
