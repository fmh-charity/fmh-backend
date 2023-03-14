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

@Schema(description = "Объект запроса на создание просьбы")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class WishCreationRequest {

    @Schema(name = "patientId", description = "Идентификатор пациента")
    Integer patientId;

    @NotBlank()
    @Schema(name = "title", description = "Тема просьбы")
    String title;

    @Schema(name = "description", description = "Описание записки")
    String description;

    @Schema(name = "planExecuteDate", description = "Плановая дата исполнения")
    Instant planExecuteDate;

    @Schema(name = "wishVisibility", description = "Область видимости")
    List<Integer> wishVisibility;
}
