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

@Schema(description = "Основная информация по созданному документу")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class DocumentCreationDtoRs {
    @Schema(name = "id", description = "id в системе")
    Integer id;

    @Schema(name = "name", description = "Имя документа")
    String name;

    @Schema(name = "description", description = "Описание документа")
    String description;

    @Schema(name = "filePath", description = "Ссылка на документ")
    String filePath;

    @Schema(name = "status", description = "Статус документа")
    DocumentStatus status;

    @Schema(name = "createDate", description = "Дата создания")
    Instant createDate;

    @Schema(name = "userDtoIdFio", description = "Создатель документа")
    UserDtoIdFio user;
}
