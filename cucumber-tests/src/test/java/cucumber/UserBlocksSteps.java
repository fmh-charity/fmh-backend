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
import ru.iteco.cucumber.model.block.BlockDtoRq;
import ru.iteco.cucumber.model.block.BlockDtoRs;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@RequiredArgsConstructor
public class UserBlocksSteps {

    private final RestTemplateUtil rest = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;
    private final ResultSteps resultSteps;
    private String jwt;

    private final String blocksUrl = "/blocks";


    @SneakyThrows
    @When("Просматривает список блоков")
    public void getBlocks() {
        jwt = userCommonSteps.getJwt();
        String responseBody = rest
                .getRq(userCommonSteps.getJwt(), blocksUrl)
                .getBody();
        assertNotNull(responseBody);
        List<BlockDtoRs> blocks = objectMapper.readValue(responseBody, ArrayList.class);
        assertNotNull(blocks);
        log.info("SIZE: {}", blocks.size());
        log.info("ALL BLOCKS: {}", blocks);
    }

    @SneakyThrows
    @And("Создает новый блок: {string}, {string}")
    public void createBlock(String name, String comment) {
        BlockDtoRq blockDtoRq = BlockDtoRq.builder()
                .name(name)
                .comment(comment)
                .build();
        blockDtoRq.setName(name);
        blockDtoRq.setComment(comment);
        String responseBody = rest
                .postRq(jwt, objectMapper.writeValueAsString(blockDtoRq), blocksUrl)
                .getBody();
        assertNotNull(responseBody);
        BlockDtoRs blockDtoRs = objectMapper.readValue(responseBody, BlockDtoRs.class);
        assertNotNull(blockDtoRs);
        log.info("BLOCK: {}", blockDtoRs);
    }

    @SneakyThrows
    @And("Просматривает блок: {int}")
    public void getCreatedBlock(int id) {
        String responseBody = rest
                .getRq(jwt, blocksUrl + "/" + id)
                .getBody();
        assertNotNull(responseBody);
        BlockDtoRs blockDtoRs = objectMapper.readValue(responseBody, BlockDtoRs.class);
        assertNotNull(blockDtoRs);
        assertEquals(id, blockDtoRs.getId());
        log.info("BLOCK: {}", blockDtoRs);
    }

    @SneakyThrows
    @And("Редактирует созданный блок: {string}, {string}, {int}")
    public void editCreatedBlock(String editedName, String editedComment, int id) {
        BlockDtoRq dtoRq = BlockDtoRq.builder()
                .name(editedName)
                .comment(editedComment)
                .build();
        String responseBody = rest
                .putRq(jwt, objectMapper.writeValueAsString(dtoRq), blocksUrl + "/" + id)
                .getBody();
        assertNotNull(responseBody);
        BlockDtoRs editedDto = objectMapper.readValue(responseBody, BlockDtoRs.class);
        log.info("EDITED BLOCK: " + editedDto);
    }

    @And("Удаляет созданный блок: {int}")
    public void deleteCreatedBlock(int id) {
        String idUrl = "/" + id;
        rest.deleteRq(jwt, blocksUrl + idUrl);
//        assertEquals(
//                rest.getRq(jwt, nurseStationsUrl + idUrl)
//                        .getStatusCode()
//                        .value(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//                        .value());
        resultSteps.setStatus("SUCCESS");
    }

}
