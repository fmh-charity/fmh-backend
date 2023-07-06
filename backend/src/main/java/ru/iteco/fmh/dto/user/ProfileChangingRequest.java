package ru.iteco.fmh.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.validation.age.Age;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Информация по изменению данных о пользователе")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ProfileChangingRequest {

    @NotBlank()
    @Schema(name = "lastName", description = "Фамилия")
    String lastName;

    @NotBlank()
    @Schema(name = "firstName", description = "Имя")
    String firstName;

    @NotBlank()
    @Schema(name = "middleName", description = "Отчество")
    String middleName;

    @Age(min = 18, max = 100, message = "Возраст должен быть больше 18 и меньше 100 лет")
    @Schema(name = "dateOfBirth", description = "Дата рождения")
    LocalDate dateOfBirth;

    @NotBlank
    @Email(message = "Некорректный адрес электронной почты")
    @Schema(name = "email", description = "Электронная почта")
    String email;

    @NotEmpty
    @Schema(name = "roleIds", description = "Множество идентификаторов ролей пользователя")
    Set<Integer> roleIds;
}
