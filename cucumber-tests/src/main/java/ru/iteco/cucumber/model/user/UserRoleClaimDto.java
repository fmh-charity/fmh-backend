package ru.iteco.cucumber.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleClaimDto {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("role")
    String role;
    @JsonProperty("status")
    RoleClaimStatus status;
    @JsonProperty("createdAt")
    Date createdAt;
}
