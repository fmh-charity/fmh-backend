package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.BackendUrls;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.iteco.cucumber.model.DocumentForAdminPaginationRs;
import ru.iteco.cucumber.model.DocumentForAdminRs;
import ru.iteco.cucumber.model.DocumentInfoDto;
import ru.iteco.cucumber.model.DocumentInfoPaginationDto;


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
    @When("Просматривает список информации о документах")
    public void getDocumentsInfo(){
        jwt = userCommonSteps.getJwt();
        String responseBody = restTemplateUtil
                .getRq(jwt, BackendUrls.DOCUMENTS_BASE_URL)
                .getBody();
        assertNotNull(responseBody);
        DocumentInfoPaginationDto documentInfoPaginationDto = objectMapper.readValue(responseBody, DocumentInfoPaginationDto.class);
        assertNotNull(documentInfoPaginationDto);
        log.info("SIZE: {}", documentInfoPaginationDto.getElements().size());
        log.info("ALL DOCUMENT INFO: {}", documentInfoPaginationDto);
    }


    @SneakyThrows
    @And("Возвращает список документов для администратора")
    public void getDocumentsForAdmin() {
        String responseBody = restTemplateUtil
                .getRq(jwt, BackendUrls.DOCUMENTS_BASE_URL + BackendUrls.ADMIN_BASE_URL)
                .getBody();
        assertNotNull(responseBody);
        DocumentForAdminPaginationRs documentForAdminPaginationRs = objectMapper.readValue(responseBody, DocumentForAdminPaginationRs.class);
        assertNotNull(documentForAdminPaginationRs);
        log.info("SIZE: {}", documentForAdminPaginationRs.getElements().size());
        log.info("ALL DOCUMENT INFO FOR ADMIN: {}", documentForAdminPaginationRs.getElements());
    }
}
