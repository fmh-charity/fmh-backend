package ru.iteco.cucumber.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileChangingRequest {

    @JsonProperty("lastName")
    String lastName;

    @JsonProperty("firstName")
    String firstName;

    @JsonProperty("middleName")
    String middleName;

    @JsonProperty("dateOfBirth")
    String dateOfBirth;

    @JsonProperty("email")
    String email;

    @JsonProperty("roleIds")
    Set<String> roleIds;
}
