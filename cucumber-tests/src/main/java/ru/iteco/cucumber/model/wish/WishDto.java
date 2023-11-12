package ru.iteco.cucumber.model.wish;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.iteco.cucumber.model.RoomDtoRs;
import ru.iteco.cucumber.model.patient.PatientDtoIdFio;
import ru.iteco.cucumber.model.user.UserDtoIdFio;

import java.util.List;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat
public class WishDto {
    @JsonProperty
    Integer id;

    @JsonProperty
    PatientDtoIdFio patient;

    @JsonProperty
    String title;

    @JsonProperty
    String description;

    @JsonProperty
    UserDtoIdFio creator;

    @JsonProperty
    Long createDate;

    @JsonProperty
    Long planExecuteDate;

    @JsonProperty
    Long factExecuteDate;

    @JsonProperty
    Status status;

    @JsonProperty
    RoomDtoRs room;

    @JsonProperty
    List<Integer> wishVisibility;

    @JsonProperty
    List<WishExecutorDtoRs> wishExecutors;

    @JsonProperty
    WishPriority wishPriority;

    @JsonProperty
    Boolean helpRequest;
}
