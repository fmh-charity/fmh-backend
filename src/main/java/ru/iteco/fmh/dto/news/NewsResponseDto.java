package ru.iteco.fmh.dto.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@ApiModel(description = "новости")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsResponseDto {
    @ApiModelProperty("идентификатор новости")
    int id;
    @ApiModelProperty("идентификатор категории новости")
    int newsCategoryId;
    @ApiModelProperty("заголовок новости")
    String title;
    @ApiModelProperty("описание новости")
    String description;
    @ApiModelProperty("идентификатор создателя")
    int creatorId;
    @ApiModelProperty("дата создания")
    LocalDateTime createDate;
    @ApiModelProperty("дата для публикации")
    LocalDateTime publishDate;
    @ApiModelProperty("признак для публикации новости")
    boolean publishEnabled;
}
