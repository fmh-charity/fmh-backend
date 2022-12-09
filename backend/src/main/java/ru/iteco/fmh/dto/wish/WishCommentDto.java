package ru.iteco.fmh.dto.wish;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(name = "Комментарий к заявке")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class WishCommentDto {

    @Schema(name = "id", description = "Идентификатор комментария к просьбе")
    Integer id;

    @Schema(name = "wishId", description = "Идентификатор просьбы к которой создан комментарий")
    Integer wishId;

    @Schema(name = "description", description = "Описание комментария к просьбе")
    String description;

    @Schema(name = "creatorId", description = "Идентификатор создателя комментария к просьбе")
    Integer creatorId;

    @Schema(name = "createDate", description = "Дата создания комментария к просьбе")
    Long createDate;
}