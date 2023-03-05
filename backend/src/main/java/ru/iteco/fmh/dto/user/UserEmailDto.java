package ru.iteco.fmh.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Адрес почты пользователя")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class UserEmailDto {

    @Schema(name = "name", description = "имя почты")
    String name;

    @Schema(name = "isConfirmed", description = "Признак подтверждённой почты")
    boolean isConfirmed;
}
