package ru.iteco.fmh.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
    private String login;
    private String password;


    @JsonCreator
    public LoginRequest(@JsonProperty("login") String login,
                        @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }
}
