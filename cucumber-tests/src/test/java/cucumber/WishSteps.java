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
import ru.iteco.cucumber.model.wish.WishDto;
import ru.iteco.cucumber.model.wish.WishPaginationDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RequiredArgsConstructor
public class WishSteps {
    private final RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserCommonSteps userCommonSteps;
    private final ResultSteps resultSteps;
    private String jwt;

    @SneakyThrows
    @When("Пользователь просматривает список заявок на выполнение {string} {string} по фильтру {string} {string} {string}")
    public void getAllWishes(String pages, String elements, String searchValue, String sortDirection, String sortField){
        jwt = userCommonSteps.getJwt();
        String responseBody = restTemplateUtil
                .getRq(jwt, BackendUrls.WISHES_BASE_URL + String.format("?pages=%s&elements=%s&searchValue=%s&sortDirection=%s&" +
                        "sortField=%s", pages, elements, searchValue, sortDirection, sortField))
                .getBody();
        assertNotNull(responseBody);
        TypeReference<WishPaginationDto> wishes = new TypeReference<>(){};
        WishPaginationDto listWishDto = objectMapper.readValue(responseBody, wishes);
        assertNotNull(listWishDto);
        Assertions.assertNotEquals(0, listWishDto.getElements().size());
        log.info("SIZE: {}", listWishDto.getElements().size());
        log.info("ALL USERS INFO: {}", listWishDto);
    }
}
