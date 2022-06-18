package ru.iteco.cucumber.model.block;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.cucumber.model.room.RoomDtoRq;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockDtoRq {

    private String name;

    private String comment;

    private Set<RoomDtoRq> roomDtoRqSet;

    boolean deleted;

}
