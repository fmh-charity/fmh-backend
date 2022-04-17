package ru.iteco.cucumber.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {

    private String clientId;
    private String clientSecret;
    private String audience;
    private String grantType;

}
