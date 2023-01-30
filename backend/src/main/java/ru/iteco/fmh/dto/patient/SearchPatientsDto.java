package ru.iteco.fmh.dto.patient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

/**
 * Представление поиска пациента.
 */
@ToString
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@Schema(description = "Представление поиска сущности пациента")
public class SearchPatientsDto {

    //allowEmptyValue = true
    @Schema(name = "status", description = "Статус госпитализации")
    String status;
}