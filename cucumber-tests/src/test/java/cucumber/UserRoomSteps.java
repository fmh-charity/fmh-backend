package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.BackendUrls;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

    private final String roomsUrl = BackendUrls.ROOMS_BASE_URL;
    private RoomDtoRs roomDtoRs;


    @SneakyThrows
    @When("Просматривает список палат")
    public void getAllRooms() {
        jwt = userCommonSteps.getJwt();
        String responseBody = rest
                .getRq(userCommonSteps.getJwt(), roomsUrl)
                .getBody();
        assertNotNull(responseBody);
        ArrayList<RoomDtoRs> rooms = objectMapper.readValue(responseBody, ArrayList.class);
        assertNotNull(rooms);
        log.info("SIZE: {}", rooms.size());
        log.info("ALL ROOMS: {}", rooms);
    }

    @SneakyThrows
    @And("Создает новую палату: {string}, {string}, {string}, {string}")
    public void createRoom(String name, String nurseStationId, String maxOccupancy, String comment) {
        RoomDtoRq roomDtoRq = RoomDtoRq.builder()
                .name(name)
                .nurseStationId(Integer.parseInt(nurseStationId))
                .maxOccupancy(Integer.parseInt(maxOccupancy))
                .comment(comment)
                .build();
        String responseBody = rest
                .postRq(jwt, objectMapper.writeValueAsString(roomDtoRq), roomsUrl)
                .getBody();
        assertNotNull(responseBody);
        roomDtoRs = objectMapper.readValue(responseBody, RoomDtoRs.class);
        assertNotNull(roomDtoRs);
        log.info("ROOM: {}", roomDtoRs);
    }

    @SneakyThrows
    @And("Просматривает карточку созданной палаты")
    public void getCreatedRoom() {
        String responseBody = rest
                .getRq(jwt, roomsUrl + "/" + roomDtoRs.getId())
                .getBody();
        assertNotNull(responseBody);
        RoomDtoRs roomDtoRs = objectMapper.readValue(responseBody, RoomDtoRs.class);
        assertNotNull(this.roomDtoRs);
        assertEquals(roomDtoRs.getId(), this.roomDtoRs.getId());
        log.info("ROOM: {}", roomDtoRs);
    }

    @SneakyThrows
    @And("Редактирует созданную палату: {string}, {string}, {string}, {string}")
    public void editCreatedRoom(String name, String nurseStationId, String maxOccupancy, String comment) {
        roomDtoRs.setName(name);
        roomDtoRs.setNurseStationId(Integer.parseInt(nurseStationId));
        roomDtoRs.setMaxOccupancy(Integer.parseInt(maxOccupancy));
        roomDtoRs.setComment(comment);
        String responseBody = rest
                .putRq(jwt, objectMapper.writeValueAsString(roomDtoRs), roomsUrl + "/" + roomDtoRs.getId())
                .getBody();
        assertNotNull(responseBody);
        RoomDtoRs editedDto = objectMapper.readValue(responseBody, RoomDtoRs.class);
        log.info("EDITED ROOM: " + editedDto);
    }

    @And("Удаляет созданную палату")
    public void deleteCreatedRoom() {
        int idUrl = roomDtoRs.getId();
        rest.deleteRq(jwt, roomsUrl + "/" + idUrl);
    }
}
