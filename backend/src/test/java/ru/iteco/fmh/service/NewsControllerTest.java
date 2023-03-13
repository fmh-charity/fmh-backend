package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.NewsController;
import ru.iteco.fmh.dao.repository.NewsRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.exceptions.NoRightsException;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.security.RequestContext;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
public class NewsControllerTest {
    @Autowired
    NewsController sut;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ConversionService conversionService;

    @Test
    public void getAllNewsShouldPassSuccess() {
        //given
        List<String> expected = Stream.of("news-title1", "news-title8", "news-title7",
                "news-title5", "news-title4", "news-title3", "news-title2", "news-title9", "news-title6")
                .sorted().collect(Collectors.toList());
        RequestContext.setCurrentUser(getUser(Set.of(getRole("ROLE_ADMINISTRATOR"))));
        List<String> result = Objects.requireNonNull(sut.getNews(0, 9, true, null, null, null)
                        .getBody()).getElements().stream().map(NewsDto::getTitle).sorted().collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void getOnlyPublishedNewsShouldPassSuccess() {
        //given
        List<String> expected = Stream.of("news-title1", "news-title2", "news-title3", "news-title4",
                "news-title5", "news-title6", "news-title7", "news-title8").sorted().collect(Collectors.toList());
        RequestContext.setCurrentUser(getUser(Set.of(getRole("ROLE_MEDICAL_WORKER"))));

        List<String> result = Objects.requireNonNull(sut.getNews(0, 9, true, null, null, null)
                        .getBody()).getElements().stream().map(NewsDto::getTitle).sorted().collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void createAndDeleteNewsShouldPassSuccess() {
        // given
        NewsDto givenDto = getNewsDto();
        givenDto.setNewsCategoryId(1);

        NewsDto resultDto = sut.createNews(givenDto);
        Integer resultId = resultDto.getId();

        assertNotNull(resultId);

        givenDto.setId(resultId);
        givenDto.setCreatorName(resultDto.getCreatorName());
        givenDto.setCreatorId(resultDto.getCreatorId());
        givenDto.setCreateDate(resultDto.getCreateDate());
        assertEquals(givenDto, resultDto);

        // AFTER - deleting result entity
        sut.deleteNews(resultId);
        assertTrue(newsRepository.findById(resultId).orElseThrow().isDeleted());
    }

    @Test
    public void getNewsShouldPassSuccess() {
        // given
        int newsId = 1;
        RequestContext.setCurrentUser(getUser(Set.of(getRole("ROLE_ADMINISTRATOR"))));
        NewsDto expected = conversionService.convert(newsRepository.findById(newsId).get(), NewsDto.class);
        NewsDto result = sut.getNews(newsId);

        assertEquals(expected, result);
    }

    @Test
    public void getNewsShouldThrowException() {
        // given
        int newsId = 10;
        RequestContext.setCurrentUser(getUser(Set.of(getRole("ROLE_GUEST"))));
        assertThrows(NoRightsException.class, () -> sut.getNews(newsId));
    }

    @Test
    public void updateNewsShouldPassSuccess() {
        // given
        int newsId = 1;
        NewsDto given = conversionService.convert(newsRepository.findById(newsId).get(), NewsDto.class);
        String initialDescription = given.getDescription();
        given.setDescription("new Description");

        NewsDto result = sut.updateNews(given);

        assertEquals(given, result);

        // After
        given.setDescription(initialDescription);
        newsRepository.save(conversionService.convert(given, News.class));
    }

    @Test
    public void deleteNewsShouldPassSuccess() {
        // given
        int newsId = 1;

        sut.deleteNews(newsId);

        News result = newsRepository.findById(newsId).get();

        assertTrue(result.isDeleted());

        // After
        result.setDeleted(false);
        newsRepository.save(result);
    }
}

