package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.iteco.cucumber.model.NurseStationDto;
import ru.iteco.cucumber.model.NurseStationDtoRq;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@RequiredArgsConstructor
public class UserNurseStationSteps {

    private final RestTemplateUtil rest = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;
    private final ResultSteps resultSteps;
    private String jwt;

    private final String nurseStationsUrl = "/nurse_stations";

    private NurseStationDto nurseStationDto;


    @SneakyThrows
    @When("Просматривает список постов")
    public void getPosts() {
        jwt = userCommonSteps.getJwt();
        String responseBody = rest
                .getRq(userCommonSteps.getJwt(), nurseStationsUrl)
                .getBody();
        assertNotNull(responseBody);
        List<NurseStationDto> posts = objectMapper.readValue(responseBody, ArrayList.class);
        assertNotNull(posts);
        log.info("SIZE: {}", posts.size());
        log.info("ALL POSTS: {}", posts);
    }

    @SneakyThrows
    @And("Создает новый пост: {string}, {string}")
    public void createPost(String name, String comment) {
        NurseStationDtoRq nurseStation = NurseStationDtoRq.builder()
                .id(0)
                .name(name)
                .comment(comment)
                .build();
        nurseStation.setName(name);
        nurseStation.setComment(comment);
        String responseBody = rest
                .postRq(jwt, objectMapper.writeValueAsString(nurseStation), nurseStationsUrl + "/" + nurseStation.getId())
                .getBody();
        assertNotNull(responseBody);
        nurseStationDto = objectMapper.readValue(responseBody, NurseStationDto.class);
        assertNotNull(nurseStationDto);
        log.info("POST: {}", nurseStationDto);
    }

    @SneakyThrows
    @And("Просматривает карточку созданного поста")
    public void getCreatedPost() {
        String responseBody = rest
                .getRq(jwt, nurseStationsUrl + "/" + nurseStationDto.getId())
                .getBody();
        assertNotNull(responseBody);
        NurseStationDto nurseStation = objectMapper.readValue(responseBody, NurseStationDto.class);
        assertNotNull(nurseStation);
        assertEquals(nurseStationDto.getId(), nurseStation.getId());
        log.info("POST: {}", nurseStation);
    }

    @SneakyThrows
    @And("Редактирует созданный пост: {string}, {string}")
    public void editCreatedPost(String editedName, String editedComment) {
        NurseStationDtoRq dtoRq = NurseStationDtoRq.builder()
                .id(nurseStationDto.getId())
                .name(editedName)
                .comment(editedComment)
                .build();
        String responseBody = rest
                .putRq(jwt, objectMapper.writeValueAsString(dtoRq), nurseStationsUrl + "/" + dtoRq.getId())
                .getBody();
        assertNotNull(responseBody);
        NurseStationDto editedDto = objectMapper.readValue(responseBody, NurseStationDto.class);
        log.info("EDITED POST: " + editedDto);
    }

    @And("Удаляет созданный пост")
    public void deleteCreatedPost() {
        String idUrl = "/" + nurseStationDto.getId();
        rest.deleteRq(jwt, nurseStationsUrl + idUrl);
//        assertEquals(
//                rest.getRq(jwt, nurseStationsUrl + idUrl)
//                        .getStatusCode()
//                        .value(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//                        .value());
        resultSteps.setStatus("SUCCESS");
    }

}
