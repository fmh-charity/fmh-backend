package ru.iteco.fmh.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtResponse {

    private String accessToken;
    private String refreshToken;


    @JsonCreator
    public JwtResponse(@JsonProperty("accessToken") String accessToken,
                       @JsonProperty("refreshToken") String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
