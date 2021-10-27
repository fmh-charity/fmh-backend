package ru.iteco.fmh.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
