package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.utils.BackendUrls;
import cucumber.utils.RestTemplateUtil;
import io.cucumber.java.en.And;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.iteco.cucumber.model.NewsPaginationDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@RequiredArgsConstructor
public class UserAuthAndOpenNews {

    private final RestTemplateUtil rest = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;
    private final ResultSteps resultSteps;
    private final String newsUrl = BackendUrls.NEWS_BASE_URL;
    private String jwt;

    @SneakyThrows
    @And("Пользователь просматривает список доступных новостей")
    public void getAllNews() {
        jwt = userCommonSteps.getJwt();
        String responseBody = rest
                .getRq(jwt, newsUrl)
                .getBody();
        assertNotNull(responseBody);
        NewsPaginationDto news = objectMapper.readValue(responseBody, NewsPaginationDto.class);
        assertNotNull(news);
        log.info("SIZE: {}", news.getElements().size());
        log.info("ALL NEWS: {}", news.getElements());
    }

    @SneakyThrows
    @And("Пользователь просматривает новость {string}")
    public void getNewsById(String news) {
        int id = Integer.parseInt(news);
        String responseBody = rest
                .getRq(jwt, newsUrl + "/" + id)
                .getBody();
        assertNotNull(responseBody);
        log.info("NEWS: {}", responseBody);
        resultSteps.setStatus("SUCCESS");
    }

}
