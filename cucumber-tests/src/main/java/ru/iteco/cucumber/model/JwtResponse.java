package ru.iteco.cucumber.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@ToString
public class JwtResponse {

    private String accessToken;
    private String refreshToken;

}
