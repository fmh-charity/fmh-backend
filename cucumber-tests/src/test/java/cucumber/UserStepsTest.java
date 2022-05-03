package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.iteco.cucumber.model.UserInput;
import java.util.Objects;
import static org.junit.Assert.assertEquals;

@Slf4j
public class UserStepsTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUrl = "http://localhost:8080/fmh";

    private String jwt;
    private String status = "SUCCESS";
    private final String ERROR = "ERROR";

    @SneakyThrows
    @Given("1. Пользователь вводит логин {string} и пароль {string}")
    public void login(String login, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        UserInput input = new UserInput();
        input.setLogin(login);
        input.setPassword(password);

        RequestEntity<String> requestEntity = RequestEntity
                .post(baseUrl + "/authentication/login")
                .headers(headers)
                .body(objectMapper.writeValueAsString(input));;

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate
                    .exchange(Objects.requireNonNull(requestEntity), String.class);
            parseToken(Objects.requireNonNull(responseEntity.getBody()));
        } catch (RestClientException e) {
            jwt = "Unauthorized";
        } finally {
            log.info("TOKEN: {}", jwt);
        }
    }

    @When("2. получен {string}")
    public void checkingUserLogging(String token) {
        String result = "Unauthorized";
        if(!jwt.equals("Unauthorized")) {
            result = "TOKEN";
        }
        assertEquals(token, result);
    }

    @SneakyThrows
    @And("3. получена информация по пользователю")
    public void getUSerInfo() {
        if(!jwt.equals("Unauthorized")) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, "*/*");
            headers.set(HttpHeaders.AUTHORIZATION, jwt);

            RequestEntity<Void> requestEntity = RequestEntity
                    .get(baseUrl + "/authentication/userInfo")
                    .headers(headers)
                    .build();

            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
//            UserShortInfoDto user = objectMapper
//                    .readValue(responseEntity.getBody(), UserShortInfoDto.class);
            log.info("USER INFO: {}", responseEntity.getBody());
        }
        else {
            status = ERROR;
        }
    }

    @SneakyThrows
    @And("4. пользователь просматривает список доступных новостей")
    public void getAllNews() {
        if(!jwt.equals("Unauthorized")) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, "*/*");
            headers.set(HttpHeaders.AUTHORIZATION, jwt);
            RequestEntity<Void> requestEntity = RequestEntity
                    .get(baseUrl + "/news")
                    .headers(headers)
                    .build();
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

//            List<NewsDto> news = objectMapper.readValue(responseEntity.getBody(), ArrayList.class);
//            log.info("SIZE: {}", Objects.requireNonNull(news).size());
            log.info("ALL NEWS: {}", responseEntity.getBody());
        }
        else {
            status = ERROR;
        }
    }

    @SneakyThrows
    @And("5. пользователь просматривает новость {string}")
    public void getNewsById(String news) {
        if(!jwt.equals("Unauthorized")) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, "*/*");
            headers.set(HttpHeaders.AUTHORIZATION, jwt);

            RequestEntity<Void> requestEntity = RequestEntity
                    .get(baseUrl + "/news/" + news)
                    .headers(headers)
                    .build();
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

//            NewsDto newsDto = objectMapper.readValue(responseEntity.getBody(), NewsDto.class);
            log.info("NEWS: {}", responseEntity.getBody());
        }
        else {
            status = ERROR;
        }
    }

    @Then("результат {string}")
    public void result(String result) {
        assertEquals(status, result);
    }


    private void parseToken(String response) {
        jwt = response.split(",")[0].split(":")[1].replace("\"", "");
    }

    private void delay(Integer ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
