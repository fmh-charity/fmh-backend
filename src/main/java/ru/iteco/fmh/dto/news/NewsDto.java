package ru.iteco.fmh.dto.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@ApiModel(description = "новости")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsDto {
    @ApiModelProperty("идентификатор новости")
    private Integer id;
    @ApiModelProperty("идентификатор категории новости")
    private Integer newsCategoryId;
    @ApiModelProperty("заголовок новости")
    private String title;
    @ApiModelProperty("описание новости")
    private String description;
    @ApiModelProperty("идентификатор создателя")
    private Integer creatorId;
    @ApiModelProperty("дата создания")
    private Long createDate;
    @ApiModelProperty("дата для публикации")
    private Long publishDate;
    @ApiModelProperty("признак для публикации новости")
    private boolean publishEnabled;
}
