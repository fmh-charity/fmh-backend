package ru.iteco.fmh.dto.news;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(name = "Категория новости")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class NewsCategoryDto {

    @Schema(name = "id", description = "Идентификатор категории новости")
    Integer id;

    @Schema(name = "id", description = "Название категории новости")
    String name;
}