package ru.iteco.fmh.dto.wish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Объект запроса на обновление просьбы")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class WishUpdateRequest {

    @Schema(name = "patientId", description = "Идентификатор пациента")
    Integer patientId;

    @Schema(name = "title", description = "Тема просьбы")
    @NotBlank
    String title;

    @Schema(name = "executorId", description = "Идентификатор исполнителя")
    Integer executorId;

    @Schema(name = "description", description = "Описание записки")
    String description;

    @Schema(name = "planExecuteDate", description = "Плановая дата исполнения")
    Instant planExecuteDate;

    @Schema(name = "wishVisibility", description = "Область видимости")
    List<Integer> wishVisibility;

    @Schema(name = "description", description = "Признак Нужная помощь")
    Boolean helpRequest;
}
