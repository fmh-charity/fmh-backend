package ru.iteco.fmh.security;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.validation.age.Age;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {
    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁ-]+$", message = "Допустимы заглавные и строчные буквы русского алфавита и символ -")
    @Size(min = 1, max = 30, message = "Допустимая длина от 1 до 30 символов")
    String firstName;

    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁ-]+$", message = "Допустимы заглавные и строчные буквы русского алфавита и символ -")
    @Size(min = 1, max = 30, message = "Допустимая длина от 1 до 30 символов")
    String lastName;

    @Pattern(regexp = "^[а-яА-ЯёЁ-]+$")
    @Size(min = 1, max = 30, message = "Допустимы заглавные и строчные буквы русского алфавита и символ -")
    String middleName;

    @Age(min = 18, max = 100, message = "Возраст должен быть больше 18 и меньше 100 лет")
    LocalDate dateOfBirth;

    List<Integer> roleIds;

    @Pattern(regexp = "[A-Za-z\\d.!_%-+]+@[A-Za-z\\d.-]+\\.[A-Za-z]{2,4}",
            message = "Допустимы заглавные и строчные буквы латинского алфавита и цифры без пробелов")
    @Size(max = 45, message = "Допустимая длина до 45 символов")
    String email;

    @Pattern(regexp = "[A-Za-z\\d!#$%&_-]+$",
            message = "Допустимы заглавные и строчные буквы латинского алфавита, цифры и специальные символы ! # $ % & _ -")
    String password;
}
