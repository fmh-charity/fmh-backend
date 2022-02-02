package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.NewsController;
import ru.iteco.fmh.dao.repository.NewsCategoryRepository;
import ru.iteco.fmh.dao.repository.NewsRepository;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.user.User;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
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
        List<String> expected = Stream.of("news-title1", "news-title8", "news-title7", "news-title6",
                "news-title5", "news-title4", "news-title3", "news-title2", "news-title9").sorted().collect(Collectors.toList());
        TestUser testUser = new TestUser();
        testUser.login = "login1";
        List<String> result = sut.getAllNews(testUser).stream()
                .map(NewsDto::getTitle).sorted().collect(Collectors.toList());
        assertEquals(expected,result);
    }

    @Test
    public void getOnlyPublishedNewsShouldPassSuccess() {
        //given
        List<String> expected = Stream.of("news-title1", "news-title2", "news-title3", "news-title4",
                "news-title5", "news-title6", "news-title7", "news-title8").sorted().collect(Collectors.toList());
        TestUser testUser = new TestUser();
        testUser.login = "login3";
        List<String> result = sut.getAllNews(testUser).stream()
                .map(NewsDto::getTitle).sorted().collect(Collectors.toList());
        assertEquals(expected,result);
    }

    @Test
    public void createNewsShouldPassSuccess() {
        // given
        NewsDto givenDto = getNewsDto();
        givenDto.setId(0);
        givenDto.setNewsCategoryId(1);
        givenDto.setCreatorId(1);
        givenDto.setCreatorName("Смирнов Николай Петрович");

        NewsDto resultDto = sut.createNews(givenDto);

        Integer resultId = resultDto.getId();

        assertNotNull(resultId);

        givenDto.setId(resultId);
        assertEquals(givenDto, resultDto);

        // AFTER - deleting result entity
        newsRepository.deleteById(resultId);
    }

    @Test
    public void getNewsShouldPassSuccess() {
        // given
        int newsId = 1;

        NewsDto expected = conversionService.convert(newsRepository.findById(newsId).get(), NewsDto.class);
        NewsDto result = sut.getNews(newsId);

        assertEquals(expected, result);
    }

    @Test
    public void getNewsShouldThrowException() {
        // given
        int newsId = 10;

        assertThrows(IllegalArgumentException.class, () -> sut.getNews(newsId));
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
    private class TestUser implements Principal {
        String login;
        String password;


        @Override
        public String getName() {
            return login;
        }

        @Override
        public boolean implies(Subject subject) {
            return Principal.super.implies(subject);
        }
    }
}

