package ru.iteco.fmh.dto.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.time.Instant;

@Schema(description = "Информация о документе")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentInfoDto {

    @Schema(name = "id", description = "Идентификатор документа")
    Integer id;

    @Schema(name = "filePath", description = "Полная ссылка на документ")
    String filePath;

    @Schema(name = "description", description = "Описание")
    String description;
}
