package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.iteco.cucumber.model.RoomDto;
import ru.iteco.cucumber.model.RoomDtoRq;
import ru.iteco.cucumber.model.RoomDtoRs;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RequiredArgsConstructor
public class UserRoomSteps {

    private final RestTemplateUtil rest = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;
    private final ResultSteps resultSteps;
    private String jwt;

    private final String roomsUrl = "/rooms";

    private RoomDto roomDto;


    @SneakyThrows
    @When("Просматривает список палат")
    public void getAllRooms() {
        jwt = userCommonSteps.getJwt();
        String responseBody = rest
                .getRq(userCommonSteps.getJwt(), roomsUrl)
                .getBody();
        assertNotNull(responseBody);
        ArrayList<RoomDto> rooms = objectMapper.readValue(responseBody, ArrayList.class);
        assertNotNull(rooms);
        log.info("SIZE: {}", rooms.size());
        log.info("ALL ROOMS: {}", rooms);
    }

    @SneakyThrows
    @And("Создает новую палату: {string}, {string}, {string}, {string}, {string}")
    public void createRoom(String name, String blockId, String nurseStationId, String maxOccupancy, String comment) {
        RoomDtoRq roomDtoRq = RoomDtoRq.builder()
                .name(name)
                .blockId(Integer.parseInt(blockId))
                .nurseStationId(Integer.parseInt(nurseStationId))
                .maxOccupancy(Integer.parseInt(maxOccupancy))
                .comment(comment)
                .build();
        String responseBody = rest
                .postRq(jwt, objectMapper.writeValueAsString(roomDtoRq), roomsUrl + "/" + roomDtoRq.getId())
                .getBody();
        assertNotNull(responseBody);
        roomDto = objectMapper.readValue(responseBody, RoomDto.class);
        assertNotNull(roomDto);
        log.info("ROOM: {}", roomDto);
    }

    @SneakyThrows
    @And("Просматривает карточку созданной палаты")
    public void getCreatedRoom() {
        String responseBody = rest
                .getRq(jwt, roomsUrl + "/" + roomDto.getId())
                .getBody();
        assertNotNull(responseBody);
        RoomDtoRs roomDtoRs = objectMapper.readValue(responseBody, RoomDtoRs.class);
        assertNotNull(roomDto);
        assertEquals(roomDtoRs.getId(), roomDto.getId());
        log.info("ROOM: {}", roomDtoRs);
    }

    @SneakyThrows
    @And("Редактирует созданную палату: {string}, {string}, {string}, {string}, {string}")
    public void editCreatedRoom(String name, String blockId, String nurseStationId, String maxOccupancy, String comment) {
        RoomDtoRq dtoRq = RoomDtoRq.builder()
                .name(name)
                .blockId(Integer.parseInt(blockId))
                .nurseStationId(Integer.parseInt(nurseStationId))
                .maxOccupancy(Integer.parseInt(maxOccupancy))
                .comment(comment)
                .build();
        String responseBody = rest
                .putRq(jwt, objectMapper.writeValueAsString(dtoRq), roomsUrl + "/" + dtoRq.getId())
                .getBody();
        assertNotNull(responseBody);
        RoomDtoRs editedDto = objectMapper.readValue(responseBody, RoomDtoRs.class);
        log.info("EDITED ROOM: " + editedDto);
    }

    @And("Удаляет созданную палату")
    public void deleteCreatedRoom() {
        String idUrl = "/" + roomDto.getId();
        rest.deleteRq(jwt, roomsUrl + idUrl);
//        assertEquals(
//                rest.getRq(jwt, roomsUrl + idUrl)
//                        .getStatusCode()
//                        .value(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//                        .value());
//        resultSteps.setStatus("SUCCESS");
    }
}
