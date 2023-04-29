package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.BackendUrls;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.iteco.cucumber.model.user.UserInfoDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RequiredArgsConstructor
public class UserInfoSteps {
    private final RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;

    @SneakyThrows
    @When("Просматривает информацию о пользователе по {string}")
    public void getUserInfo(String userId){
        String jwt = userCommonSteps.getJwt();
        String responseBody = restTemplateUtil
                .getRq(jwt, BackendUrls.USERS_BASE_URL + "/" + userId)
                .getBody();
        assertNotNull(responseBody);
        UserInfoDto userInfoDto = objectMapper.readValue(responseBody, UserInfoDto.class);
        assertNotNull(userInfoDto);
        log.info("USER INFO: {}", userInfoDto);
    }
}
