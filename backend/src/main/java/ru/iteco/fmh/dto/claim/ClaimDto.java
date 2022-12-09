package ru.iteco.fmh.dto.claim;

import static lombok.AccessLevel.PRIVATE;

import ru.iteco.fmh.model.task.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(name = "ClaimDto", description = "Request claim")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class ClaimDto {

    @Schema(name = "id", description = "Идентификатор заявки")
    Integer id;

    @Schema(name = "title", description = "Тема заявки")
    String title;

    @Schema(name = "description", description = "Описание заявки")
    String description;

    @Schema(name = "creatorId", description = "Идентификатор создателя")
    Integer creatorId;

    @Schema(name = "executorId", description = "Идентификатор исполнителя")
    Integer executorId;

    @Schema(name = "createDate", description = "Дата создания заявки")
    Long createDate;

    @Schema(name = "planExecuteDate", description = "Плановая дата исполнения заявки")
    Long planExecuteDate;

    @Schema(name = "factExecuteDate", description = "Фактическая дата исполнения заявки")
    Long factExecuteDate;

    @Schema(name = "status", description = "Статус заявки")
    Status status;

    @Schema(name = "creatorName", description = "ФИО создателя")
    String creatorName;

    @Schema(name = "executorName", description = "ФИО исполнителя")
    String executorName;
}