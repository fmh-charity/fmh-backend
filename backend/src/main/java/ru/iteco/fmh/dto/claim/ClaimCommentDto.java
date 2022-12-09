package ru.iteco.fmh.dto.claim;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(name = "Комментарий к заявке (request version)")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class ClaimCommentDto {

    @Schema(name = "id", description = "Идентификатор комментария к заявке")
    Integer id;

    @Schema(name = "claimId", description = "Идентификатор заявки к которой создан комментарий")
    Integer claimId;

    @Schema(name = "description", description = "Описание комментария к заявке")
    String description;

    @Schema(name = "creatorId", description = "Идентификатор создателя комментария к заявке")
    Integer creatorId;

    @Schema(name = "createDate", description = "Дата создания комментария к заявке")
    Long createDate;

    @Schema(name = "creatorName", description = "ФИО создателя")
    String creatorName;
}