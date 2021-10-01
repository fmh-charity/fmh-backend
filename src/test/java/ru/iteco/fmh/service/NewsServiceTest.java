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
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.service.news.NewsService;

import java.time.Instant;
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
        User user = getUser();
        when(userRepository.findUserById(any())).thenReturn(user);
        List<NewsDto> expected = newsList.stream()
                .map(news -> conversionService.convert(news, NewsDto.class)).collect(Collectors.toList());

        when(newsRepository
                .findAllByPublishDateLessThanEqualAndPublishEnabledIsTrueAndDeletedIsFalseOrderByPublishDateDesc(any()))
                .thenReturn(newsList);

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
