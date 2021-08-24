package ru.iteco.fmh.dto.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.user.UserDto;
import java.time.LocalDateTime;

@ApiModel(description = "комментарий к заявке")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishCommentDto {
    @ApiModelProperty("идентификатор комментария к просьбе")
    private Integer id;
    @ApiModelProperty("идентификатор просьбы к которой создан комментарий")
    private WishDto wish;
    @ApiModelProperty("описание комментария к просьбе")
    private String description;
    @ApiModelProperty("идентификатор создателя комментария к просьбе")
    private UserDto creator;
    @ApiModelProperty("дата создания комментария к просьбе")
    private LocalDateTime createDate;
}
