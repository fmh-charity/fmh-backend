package ru.iteco.fmh.dto.wish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Исполнитель просьбы")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class WishExecutorDtoRs {
    @Schema(name = "executorId", description = "Идентификатор исполнителя просьбы")
    Integer executorId;
    @Schema(name = "joinDate", description = "Дата присоединения к просьбе")
    Long joinDate;
    @Schema(name = "finishDate", description = "Фактическая дата выполнения просьбы")
    Long finishDate;
}
