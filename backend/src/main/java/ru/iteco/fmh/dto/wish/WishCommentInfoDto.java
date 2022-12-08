package ru.iteco.fmh.dto.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.user.UserDtoIdFio;

@ApiModel(description = "информация о комментарии к заявке")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishCommentInfoDto {
    @ApiModelProperty("создатель комментария к просьбе")
    private UserDtoIdFio userDtoIdFio;
    @ApiModelProperty("время создания комментария к просьбе")
    private Long createTime;
    @ApiModelProperty("текст комментария к просьбе")
    private String description;
    @ApiModelProperty("идентификатор комментария к просьбе")
    private Integer id;

    public WishCommentInfoDto(Long createTime, String description, Integer id) {
        this.createTime = createTime;
        this.description = description;
        this.id = id;
    }
}
