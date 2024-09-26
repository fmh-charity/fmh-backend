package ru.iteco.fmh.dto.wish;

import static lombok.AccessLevel.PRIVATE;

import ru.iteco.fmh.dto.user.UserDtoIdFio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(description = "Информация о комментарии к заявке")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class WishCommentInfoDto {

    @Schema(name = "userDtoIdFio", description = "Создатель комментария к просьбе")
    UserDtoIdFio userDtoIdFio;

    @Schema(name = "createTime", description = "Время создания комментария к просьбе")
    Long createTime;

    @Schema(name = "description", description = "Текст комментария к просьбе")
    String description;

    @Schema(name = "id", description = "Идентификатор комментария к просьбе")
    Integer id;
}