package ru.iteco.cucumber.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonFormat
public class UserShortInfoDto {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("middleName")
    private String middleName;
    @JsonProperty("admin")
    private boolean isAdmin;
    @JsonProperty("roles")
    private Set<String> roles;
    @JsonProperty("email")
    private UserEmailDto email;
    @JsonProperty("isConfirmed")
    private Boolean isConfirmed;

}
