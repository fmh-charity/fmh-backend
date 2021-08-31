package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.NewsController;
import ru.iteco.fmh.dao.repository.NewsRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;

import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getNewsDto;

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
        List<String> expected = List.of("news-title1", "news-title8", "news-title7", "news-title6",
                "news-title5", "news-title4", "news-title3", "news-title2");

        List<String> result = sut.getAllNews().stream()
                .map(NewsDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    public void createNewsShouldPassSuccess() {
        // given
        NewsDto newsDto = getNewsDto();
        newsDto.setNewsCategoryId(1);
        newsDto.setCreatorId(1);

        int resultId = sut.createNews(newsDto);

        assertNotNull(resultId);

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

        assertEquals(given.getDescription(), result.getDescription());

        // After
        given.setDescription(initialDescription);
        newsRepository.save(conversionService.convert(given, News.class));
    }

    @Test
    public void deleteNewsShouldPassSuccess() {
        // given
        int newsId = 1;

        int resultId = sut.deleteNews(newsId);

        News result = newsRepository.findById(resultId).get();

        assertTrue(result.isDeleted());

        // After
        result.setDeleted(false);
        newsRepository.save(result);
    }
}

