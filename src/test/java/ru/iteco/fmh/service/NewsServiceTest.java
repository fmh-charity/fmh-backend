package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.NewsRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.security.RequestContext;
import ru.iteco.fmh.service.news.NewsService;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getNews;
import static ru.iteco.fmh.TestUtils.getUser;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class NewsServiceTest {
    @Autowired
    NewsService sut;

    @MockBean
    NewsRepository newsRepository;
    @MockBean
    UserRepository userRepository;

    @Autowired
    ConversionService conversionService;

    @Test
    public void getAllNewsShouldPassSuccess() {
        List<News> newsList = List.of(getNews(Instant.now()), getNews(Instant.now().minusSeconds(1000)));
        User userAdmin = getUser(Collections.singletonList(Role.builder().id(1).name("ROLE_ADMINISTRATOR").deleted(false).build()));
        when(userRepository.findUserById(any())).thenReturn(userAdmin);
        List<NewsDto> expected = newsList.stream()
                .map(news -> conversionService.convert(news, NewsDto.class)).collect(Collectors.toList());
        when(newsRepository
                .findAllByPublishDateLessThanEqualAndDeletedIsFalseOrderByPublishDateDesc(any()))
                .thenReturn(newsList);
        when(userRepository.findUserByLogin(any())).thenReturn(userAdmin);
        RequestContext.setCurrentUser(userAdmin);
        List<NewsDto> result = sut.getAllNews();

        assertEquals(expected, result);
    }

    @Test
    public void getOnlyAllPublishedNewsShouldPassSuccess() {
        List<News> newsList = List.of(
                getNews(Instant.now()),
                getNews(Instant.now().minusSeconds(1000)),
                getNews(Instant.now().minusSeconds(5000), false));

        User userMedic = getUser(Collections.singletonList(Role.builder().id(2).name("ROLE_MEDICAL_WORKER").deleted(false).build()));
        when(userRepository.findUserById(any())).thenReturn(userMedic);
        List<NewsDto> expected = newsList.stream().filter(News::isPublishEnabled)
                .map(news -> conversionService.convert(news, NewsDto.class)).collect(Collectors.toList());

        when(newsRepository
                .findAllByPublishDateLessThanEqualAndDeletedIsFalseAndPublishEnabledIsTrueOrderByPublishDateDesc(any()))
                .thenReturn(newsList.stream().filter(News::isPublishEnabled).collect(Collectors.toList()));
        when(userRepository.findUserByLogin(any())).thenReturn(userMedic);
        RequestContext.setCurrentUser(userMedic);
        List<NewsDto> result = sut.getAllNews();
        assertEquals(expected, result);
    }

    @Test
    public void getNewsShouldPassSuccess() {
        // given
        News news = getNews();
        User user = getUser();
        int newsId = 1;

        when(userRepository.findUserById(any())).thenReturn(user);
        NewsDto expected = conversionService.convert(news, NewsDto.class);

        when(newsRepository.findById(any())).thenReturn(Optional.of(news));

        NewsDto result = sut.getNews(newsId);

        assertEquals(expected, result);
    }

    @Test
    public void createOrUpdateNewsShouldPassSuccess() {
        // given
        News news = getNews();
        User user = getUser();
        when(userRepository.findUserById(any())).thenReturn(user);

        NewsDto givenDto = conversionService.convert(news, NewsDto.class);

        when(newsRepository.save(any())).thenReturn(news);

        NewsDto result = sut.createOrUpdateNews(givenDto);

        assertEquals(givenDto, result);
    }

    @Test
    public void deleteNewsShouldPassSuccess() {
        // given
        int newsId = 1;
        News news = getNews();

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));
        when(newsRepository.save(news)).thenReturn(news);

        sut.deleteNews(newsId);

        assertTrue(news.isDeleted());
    }

}
