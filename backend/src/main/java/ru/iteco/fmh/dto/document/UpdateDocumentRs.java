package ru.iteco.fmh.dto.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
@Schema(description = "Основная информация по документу для возврата измененного документа")
public class UpdateDocumentRs {

    @Schema(name = "id", description = "Идентификатор документа")
    Integer id;

    @Schema(name = "name", description = "Имя документа")
    String name;

    @Schema(name = "filePath", description = "Ссылка на файл")
    String filePath;

    @Schema(name = "description", description = "Описание документа")
    String description;

    @Schema(name = "deleted", description = "Флаг удаления документа")
    boolean deleted;

    @Schema(name = "status", description = "Статус документа")
    DocumentStatus status;

    @Schema(name = "createDate", description = "Дата создания документа")
    Instant createDate;

    @Schema(name = "userDtoIdFio", description = "Информация по пользователю")
    UserDtoIdFio user;
}
