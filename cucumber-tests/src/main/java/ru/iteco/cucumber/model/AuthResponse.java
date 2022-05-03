package ru.iteco.cucumber.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonFormat
public class AuthResponse {

    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("tokenType")
    private String tokenType;

}
