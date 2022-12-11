package ru.iteco.fmh.dto.news;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(description = "Новости")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class NewsDto {

    @Schema(name = "id", description = "Идентификатор новости")
    Integer id;

    @Schema(name = "newsCategoryId", description = "Идентификатор категории новости")
    Integer newsCategoryId;

    @Schema(name = "title", description = "Заголовок новости")
    String title;

    @Schema(name = "description", description = "Описание новости")
    String description;

    @Schema(name = "creatorId", description = "Идентификатор создателя")
    Integer creatorId;

    @Schema(name = "createDate", description = "Дата создания")
    Long createDate;

    @Schema(name = "publishDate", description = "Дата для публикации")
    Long publishDate;

    @Schema(name = "publishEnabled", description = "Признак для публикации новости")
    boolean publishEnabled;

    @Schema(name = "creatorName", description = "ФИО создателя")
    String creatorName;
}