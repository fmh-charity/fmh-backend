package cucumber;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.iteco.cucumber.model.NewsDto;
import ru.iteco.cucumber.model.UserShortInfoDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@Slf4j
public class UserLogingTest {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String baseUrl = "http://localhost:8080/fmh";

    private String jwt;
    private String status = "SUCCESS";
    private final String ERROR = "ERROR";


    @Given("1. Пользователь вводит логин {string} и пароль {string}")
    public void login(String login, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        RequestEntity<String> requestEntity = RequestEntity
                .post(baseUrl + "/authentication/login")
                .headers(headers)
                .body("{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}");

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(requestEntity, String.class);
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

            Gson gson = new Gson();
            UserShortInfoDto user = gson.fromJson(responseEntity.getBody(), UserShortInfoDto.class);

            log.info("USER INFO: {}", user);
        }
        else {
            status = ERROR;
        }
    }

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

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<NewsDto>>(){}.getType();
            List<NewsDto> news = gson.fromJson(responseEntity.getBody(), listType);
            log.info("SIZE: {}", Objects.requireNonNull(news).size());
            log.info("ALL NEWS: {}", news);
        }
        else {
            status = ERROR;
        }
    }

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

            Gson gson = new Gson();
            NewsDto newsDto = gson.fromJson(responseEntity.getBody(), NewsDto.class);

            log.info("NEWS: {}", newsDto);
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
