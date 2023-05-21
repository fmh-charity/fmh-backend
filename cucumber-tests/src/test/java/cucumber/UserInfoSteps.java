package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.BackendUrls;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.iteco.cucumber.model.UserShortInfoDto;
import ru.iteco.cucumber.model.user.ProfileChangingRequest;
import ru.iteco.cucumber.model.user.UserInfoDto;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RequiredArgsConstructor
public class UserInfoSteps {
    private final RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;

    @SneakyThrows
    @When("Просматривает информацию о пользователе по {string}")
    public void getUserInfo(String userId) {
        String jwt = userCommonSteps.getJwt();
        String responseBody = restTemplateUtil
                .getRq(jwt, BackendUrls.USERS_BASE_URL + "/" + userId)
                .getBody();
        assertNotNull(responseBody);
        UserInfoDto userInfoDto = objectMapper.readValue(responseBody, UserInfoDto.class);
        assertNotNull(userInfoDto);
        log.info("USER INFO: {}", userInfoDto);
    }

    @SneakyThrows
    @And("Редактирует информацию о пользователе {string}, {string}, {string}, {string}, {string}, {string} по {string}")
    public void updateUser(String firstName, String lastName, String middleName, String dateOfBirth, String email,
                           String roleIds, String userId) {
        String jwt = userCommonSteps.getJwt();
        ProfileChangingRequest profileChangingRequest = ProfileChangingRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .roleIds(Set.copyOf(Arrays.asList(roleIds.split("\\s*,\\s*"))))
                .build();
        String responseBody = restTemplateUtil
                .putRq(jwt, objectMapper.writeValueAsString(profileChangingRequest), BackendUrls.USERS_BASE_URL + "/" + userId)
                .getBody();
        assertNotNull(responseBody);
        UserShortInfoDto userShortInfoDto = objectMapper.readValue(responseBody, UserShortInfoDto.class);
        assertNotNull(userShortInfoDto);
        log.info("USER SHORT INFO: {}", userShortInfoDto);
    }
}
