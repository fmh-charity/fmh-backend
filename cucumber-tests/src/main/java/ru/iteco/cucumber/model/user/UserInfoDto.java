package ru.iteco.cucumber.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.iteco.cucumber.model.UserEmailDto;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

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
    @JsonProperty("email")
    private UserEmailDto email;
    @JsonProperty("roles")
    private Set<String> roles;
    @JsonProperty("confirmed")
    private boolean isConfirmed;
    @JsonProperty("userRoleClaim")
    private UserRoleClaimDto userRoleClaim;

}
