package ru.iteco.cucumber.model;

import lombok.Data;

@Data
public class AuthResponse {

    private String accessToken;
    private String tokenType;

}
