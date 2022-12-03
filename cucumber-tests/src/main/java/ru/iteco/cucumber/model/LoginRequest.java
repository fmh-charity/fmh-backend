package ru.iteco.cucumber.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@ToString
public class LoginRequest {
    private String login;
    private String password;

}
