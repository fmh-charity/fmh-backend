package ru.iteco.fmh.dto.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Schema(description = "Основная информация для создания документа")
public class DocumentCreationDtoRq {
    @NotNull
    @Schema(name = "name", description = "Имя документа")
    String name;

    @Schema(name = "description", description = "Описание документа")
    String description;

    @NotNull
    @Schema(name = "filePath", description = "Ссылка на документ")
    String filePath;
}
