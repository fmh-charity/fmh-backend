package ru.iteco.fmh.dto.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.dto.user.UserDto;

import java.time.LocalDateTime;

@ApiModel(description = "новости")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsDto {
    @ApiModelProperty("идентификатор новости")
    Integer id;
    @ApiModelProperty("идентификатор категории новости")
    NewsCategoryDto newsCategory;
    @ApiModelProperty("заголовок новости")
    String title;
    @ApiModelProperty("описание новости")
    String description;
    @ApiModelProperty("идентификатор создателя")
    UserDto creator;
    @ApiModelProperty("дата создания")
    LocalDateTime createDate;
    @ApiModelProperty("дата для публикации")
    LocalDateTime publishDate;
    @ApiModelProperty("признак для публикации новости")
    boolean publishEnabled;
}
