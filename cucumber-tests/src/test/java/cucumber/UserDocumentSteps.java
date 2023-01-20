package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.BackendUrls;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.iteco.cucumber.model.DocumentInfoDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RequiredArgsConstructor
public class UserDocumentSteps {

    private final RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;
    private String jwt;

    @SneakyThrows
    @When("Просматривает список документов")
    public void getDocuments(){
        jwt = userCommonSteps.getJwt();
        String responseBody = restTemplateUtil
                .getRq(jwt, BackendUrls.DOCUMENTS_BASE_URL)
                .getBody();
        assertNotNull(responseBody);
        List<DocumentInfoDto> documentInfoDtoList = objectMapper.readValue(responseBody, ArrayList.class);
        assertNotNull(documentInfoDtoList);
        log.info("SIZE: {}", documentInfoDtoList.size());
        log.info("ALL DOCUMENT INFO: {}", documentInfoDtoList);
    }


}
