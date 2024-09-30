package ru.iteco.fmh.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.*;
import ru.iteco.fmh.dao.repository.NewsCategoryRepository;
import ru.iteco.fmh.dao.repository.NewsRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.news.NewsPaginationDto;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.security.RequestContext;
import ru.iteco.fmh.service.news.NewsService;
import ru.iteco.fmh.service.news.NewsServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static ru.iteco.fmh.TestUtils.*;



@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    NewsService sut;

    @Mock
    NewsRepository newsRepository;
    @Mock
    NewsCategoryRepository newsCategoryRepository;

    @Mock
    ConversionService conversionService;


    @Test
    public void getAllNewsShouldPassSuccess() {
        sut = new NewsServiceImpl(newsRepository, conversionService, newsCategoryRepository){
            @Override
            protected User getCurrentUser() {
                return getUser(Set.of(Role.builder().id(1)
                        .name("ROLE_ADMINISTRATOR").deleted(false).build()), getProfile());
            }
        };
        List<News> newsList = List.of(getNews(Instant.now()), getNews(Instant.now().minusSeconds(1000)));
        Pageable pageableList = PageRequest.of(0, 9, Sort.by("publishDate"));
        Page<News> pageableResult = new PageImpl<>(newsList, pageableList, 8);

        when(newsRepository.findAllWithFiltersWhereDeletedIsFalse(any(), any(), any(),
                any())).thenReturn(pageableResult);

        System.out.println("\n\n\n\n" + newsRepository
                .findAllWithFiltersWhereDeletedIsFalse(null, null,
                        null,  pageableList)
                .getContent().size() + "\n\n\n\n\n");


        NewsPaginationDto result = sut.getNews(0, 9, true,
                null, null, null);
        assertEquals(1, result.getPages());
        verify(conversionService, times(2)).convert(any(News.class), eq(NewsDto.class));


    }

    @Test
    public void getOnlyAllPublishedNewsShouldPassSuccess() {
        sut = new NewsServiceImpl(newsRepository, conversionService, newsCategoryRepository){
            @Override
            protected User getCurrentUser() {
                return getUser(Set.of(Role.builder().id(2)
                        .name("ROLE_MEDICAL_WORKER").deleted(false).build()), getProfile());
            }
        };
        List<News> newsList = List.of(
                getNews(Instant.now()),
                getNews(Instant.now().minusSeconds(1000)),
                getNews(Instant.now().minusSeconds(5000), false));
        Pageable pageableList = PageRequest.of(0, 9, Sort.by("publishDate"));
        Page<News> pageableResult = new PageImpl<>(newsList, pageableList, 8);

       when(newsRepository
                .getActualNewsInInterval(any(), any(), any(), any(), any()))
                .thenReturn(
                        new PageImpl<>(pageableResult.stream()
                                .filter(News::isPublishEnabled)
                                .collect(Collectors.toList()), pageableList, 8));


        List<NewsDto> expected = newsList.stream().filter(News::isPublishEnabled)
                .map(news -> conversionService.convert(news, NewsDto.class)).collect(Collectors.toList());
        List<NewsDto> result = sut.getNews(0, 9, true, null, null, null).getElements();

        assertEquals(expected, result);
    }

    @Test
    public void getNewsShouldPassSuccess() {
        sut = new NewsServiceImpl(newsRepository, conversionService, newsCategoryRepository){
            @Override
            protected User getCurrentUser() {
                return getUser(Set.of(Role.builder().id(1)
                        .name("ROLE_ADMINISTRATOR").deleted(false).build()), getProfile());
            }
        };
        // given
        int newsId = 10;
        News news = getNews();


        NewsDto expected = conversionService.convert(news, NewsDto.class);
        when(newsRepository.findById(any())).thenReturn(Optional.of(news));

        NewsDto result = sut.getNews(newsId);

        assertEquals(expected, result);
    }

    @Test
    public void createOrUpdateNewsShouldPassSuccess() {
        // given
       /* News news = getNews();
        User user = getUser(getProfile());
        when(userRepository.findUserById(any())).thenReturn(user);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .thenReturn(user);

        NewsDto givenDto = conversionService.convert(news, NewsDto.class);

        when(newsRepository.save(any())).thenReturn(news);

        NewsDto result = sut.createNews(givenDto);

        givenDto.setId(result.getId());
        givenDto.setCreatorName(result.getCreatorName());
        givenDto.setCreatorId(result.getCreatorId());
        givenDto.setCreateDate(result.getCreateDate());

        assertEquals(givenDto, result);*/
    }

    /*@Test
    public void deleteNewsShouldPassSuccess() {
        // given
        int newsId = 1;
        News news = getNews();

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));
        when(newsRepository.save(news)).thenReturn(news);

        sut.deleteNews(newsId);

        assertTrue(news.isDeleted());
    }*/
}
