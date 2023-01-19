package ru.iteco.fmh.dto.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.document.DocumentStatus;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Schema(description = "Основная информация по документу для запроса по изменению")
public class UpdateDocumentRq {
    @NotNull
    @Schema(name = "name", description = "Имя документа")
    String name;

    @Schema(name = "description", description = "Описание документа")
    String description;

    @Schema(name = "status", description = "Статус документа")
    DocumentStatus status;

    @NotNull
    @Schema(name = "userId", description = "Идентификатор пользователя")
    Integer userId;

}
