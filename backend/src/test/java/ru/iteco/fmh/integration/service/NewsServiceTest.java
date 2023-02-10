package ru.iteco.fmh.integration.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getNews;
import static ru.iteco.fmh.TestUtils.getUser;


@RunWith(SpringRunner.class)
@SpringBootTest()
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
@Ignore
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
        Pageable pageableList = PageRequest.of(0, 9, Sort.by("publishDate"));
        Page<News> pageableResult = new PageImpl<>(newsList, pageableList, 8);
        User userAdmin = getUser(Collections.singletonList(Role.builder().id(1)
                .name("ROLE_ADMINISTRATOR").deleted(false).build()));

        when(userRepository.findUserById(any())).thenReturn(userAdmin);
        List<NewsDto> expected = newsList.stream()
                .map(news -> conversionService.convert(news, NewsDto.class)).collect(Collectors.toList());
        when(newsRepository.findAllWithFiltersWhereDeletedIsFalse(any(), any(), any(), any())).thenReturn(pageableResult);

        System.out.println("\n\n\n\n" + newsRepository.findAllWithFiltersWhereDeletedIsFalse(null, null, null,  pageableList)
                .getContent().size() + "\n\n\n\n\n");

        when(userRepository.findUserByLogin(any())).thenReturn(userAdmin);

        RequestContext.setCurrentUser(userAdmin);
        List<NewsDto> result = sut.getNews(0, 9, true, null, null, null).getElements();

        assertEquals(expected, result);
    }

    @Test
    public void getOnlyAllPublishedNewsShouldPassSuccess() {
        List<News> newsList = List.of(
                getNews(Instant.now()),
                getNews(Instant.now().minusSeconds(1000)),
                getNews(Instant.now().minusSeconds(5000), false));
        Pageable pageableList = PageRequest.of(0, 9, Sort.by("publishDate"));
        Page<News> pageableResult = new PageImpl<>(newsList, pageableList, 8);
        User userMedic = getUser(Collections.singletonList(Role.builder().id(2)
                .name("ROLE_MEDICAL_WORKER").deleted(false).build()));

        when(userRepository.findUserById(any())).thenReturn(userMedic);
        when(userRepository.findUserByLogin(any())).thenReturn(userMedic);
        when(newsRepository
                .getActualNewsInInterval(any(), any(), any(), any(), any()))
                .thenReturn(
                        new PageImpl<>(pageableResult.stream()
                                .filter(News::isPublishEnabled)
                                .collect(Collectors.toList()), pageableList, 8));

        RequestContext.setCurrentUser(userMedic);
        List<NewsDto> expected = newsList.stream().filter(News::isPublishEnabled)
                .map(news -> conversionService.convert(news, NewsDto.class)).collect(Collectors.toList());
        List<NewsDto> result = sut.getNews(0, 9, true, null, null, null).getElements();

        assertEquals(expected, result);
    }

    @Test
    public void getNewsShouldPassSuccess() {
        // given
        int newsId = 10;
        News news = getNews();
        User user= getUser(Collections.singletonList(Role.builder().id(1).name("ROLE_ADMINISTRATOR").deleted(false).build()));

        when(userRepository.findUserById(any())).thenReturn(user);

        NewsDto expected = conversionService.convert(news, NewsDto.class);
        when(newsRepository.findById(any())).thenReturn(Optional.of(news));

        when(userRepository.findUserByLogin(any())).thenReturn(user);
        RequestContext.setCurrentUser(user);

        NewsDto result = sut.getNews(newsId);

        assertEquals(expected, result);
    }

    @Test
    public void createOrUpdateNewsShouldPassSuccess() {
        // given
        News news = getNews();
        User user = getUser();
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
