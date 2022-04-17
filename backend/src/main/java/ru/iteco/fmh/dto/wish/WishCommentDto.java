package ru.iteco.fmh.dto.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "комментарий к заявке")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishCommentDto {
    @ApiModelProperty("идентификатор комментария к просьбе")
    private Integer id;
    @ApiModelProperty("идентификатор просьбы к которой создан комментарий")
    private Integer wishId;
    @ApiModelProperty("описание комментария к просьбе")
    private String description;
    @ApiModelProperty("идентификатор создателя комментария к просьбе")
    private Integer creatorId;
    @ApiModelProperty("дата создания комментария к просьбе")
    private Long createDate;
}
