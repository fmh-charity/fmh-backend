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
public class AuthRequest {

    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("clientSecret")
    private String clientSecret;
    @JsonProperty("audience")
    private String audience;
    @JsonProperty("grantType")
    private String grantType;

}
