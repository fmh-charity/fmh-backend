package cucumber;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.BackendUrls;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import ru.iteco.cucumber.model.UserShortInfoDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RequiredArgsConstructor
public class RegistrationClaimSteps {
    private final RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;

    private String jwt;

    @SneakyThrows
    @When("Просматривает список пользователей {string} {string} по фильтрам {string} {string} {string}")
    public void getAllUsers(String pages, String elements, String text, String roleIds, String confirmed){
        jwt = userCommonSteps.getJwt();
        String responseBody = restTemplateUtil
                .getRq(jwt, BackendUrls.USERS_BASE_URL + String.format("?pages=%s&elements=%s&text=%s&roleIds=%s&" +
                        "confirmed=%s", pages, elements, text, roleIds, confirmed))
                .getBody();
        assertNotNull(responseBody);
        TypeReference<List<UserShortInfoDto>> typeReference = new TypeReference<>(){};
        List<UserShortInfoDto> listUserShortInfoDto = objectMapper.readValue(responseBody, typeReference);
        assertNotNull(listUserShortInfoDto);
        Assertions.assertNotEquals(0, listUserShortInfoDto.size());
        log.info("SIZE: {}", listUserShortInfoDto.size());
        log.info("ALL USERS INFO: {}", listUserShortInfoDto);
    }
}
