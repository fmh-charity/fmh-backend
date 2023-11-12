package ru.iteco.cucumber.model.wish;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.iteco.cucumber.model.user.UserDtoIdFio;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat
public class WishExecutorDtoRs {
    @JsonFormat
    UserDtoIdFio executor;
    @JsonFormat
    Long joinDate;
    @JsonFormat
    Long finishDate;
}
