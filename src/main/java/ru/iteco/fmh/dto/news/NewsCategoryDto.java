package ru.iteco.fmh.dto.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ApiModel(description = "категория новости")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsCategoryDto {
    @ApiModelProperty("идентификатор категории новости")
    Integer id;
    @ApiModelProperty("название категории новости")
    String name;
}
