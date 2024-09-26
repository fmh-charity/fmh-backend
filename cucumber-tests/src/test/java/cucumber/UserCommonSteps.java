package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.BackendUrls;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import ru.iteco.cucumber.model.JwtResponse;
import ru.iteco.cucumber.model.LoginRequest;
import ru.iteco.cucumber.model.UserShortInfoDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@Slf4j
@RequiredArgsConstructor
public class UserCommonSteps {

    private final RestTemplateUtil rest = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String loginUrl = BackendUrls.AUTHENTICATION_BASE_URL + "/login";
    private final String userInfoUrl = BackendUrls.AUTHENTICATION_BASE_URL + "/userInfo";

    private String jwt = "Unauthorized";

    @SneakyThrows
    @Given("Пользователь вводит логин {string} и пароль {string}")
    public void login(String login, String password) {
        LoginRequest loginRq = LoginRequest.builder()
                .login(login)
                .password(password)
                .build();
        String responseBody = rest
                .postRq(objectMapper.writeValueAsString(loginRq), loginUrl)
                .getBody();
        assertNotNull(responseBody);

        jwt = getAccessToken(responseBody);
        assertNotNull(jwt);
        assertNotEquals("Unauthorized", jwt);
        log.info("TOKEN: " + jwt);
    }

    @When("Получен {string}")
    public void checkingUserLogging(String token) {
        String result = "Unauthorized".equals(jwt)
                ? "Unauthorized"
                : "TOKEN";
        assertEquals(token, result);
    }

    @SneakyThrows
    @And("Получена информация по пользователю")
    public void getUserInfo() {
        ResponseEntity<String> responseEntity = rest
                .getRq(jwt, userInfoUrl);
        UserShortInfoDto user = objectMapper
                .readValue(responseEntity.getBody(), UserShortInfoDto.class);
        assertNotNull(user);
        log.info("USER INFO: {}", responseEntity.getBody());
    }

    @SneakyThrows
    private String getAccessToken(String response) {
        JwtResponse jwtResponse = objectMapper
                .readValue(response, JwtResponse.class);
        return jwtResponse.getAccessToken();
    }

    public String getJwt() {
        return jwt;
    }
}
