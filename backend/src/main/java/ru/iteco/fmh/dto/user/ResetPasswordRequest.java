package ru.iteco.fmh.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Информация по обновлению пароля")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ResetPasswordRequest {
    String login;
    @Size(min = 6)
    @Pattern(regexp = "[a-zA-Z0-9!#$%&_-]+")
    String password;
}
