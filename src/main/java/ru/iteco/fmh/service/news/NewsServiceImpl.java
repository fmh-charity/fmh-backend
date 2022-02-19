package ru.iteco.fmh.service.news;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.Util;
import ru.iteco.fmh.dao.repository.NewsRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.exceptions.NoRightsException;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.security.RequestContext;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;

    @Override
    public List<NewsDto> getAllNews() {
        User currentUser = RequestContext.getCurrentUser();
        Util util = new Util(userRepository);
        if (util.isAdmin(currentUser)) {
            List<News> news = newsRepository
                    .findAllByPublishDateLessThanEqualAndDeletedIsFalseOrderByPublishDateDesc(Instant.now());
            return news.stream()
                    .map(v -> conversionService.convert(v, NewsDto.class))
                    .collect(Collectors.toList());
        } else {
            List<News> news = newsRepository
                    .findAllByPublishDateLessThanEqualAndDeletedIsFalseAndPublishEnabledIsTrueOrderByPublishDateDesc(Instant.now());
            return news.stream()
                    .map(v -> conversionService.convert(v, NewsDto.class))
                    .collect(Collectors.toList());
        }
    }


    @Override
    public NewsDto getNews(int id) {

        User currentUser = RequestContext.getCurrentUser();
        Util util = new Util(userRepository);
        if (util.isAdmin(currentUser)) {
            News news = newsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Новости с таким ID не существует"));
            return conversionService.convert(news, NewsDto.class);
        } else {
            News news = newsRepository.findByIdAndPublishEnabledIsTrue(id)
                    .orElseThrow(NoRightsException::new);
            return conversionService.convert(news, NewsDto.class);
        }

    }

    @Transactional
    @Override
    public NewsDto createOrUpdateNews(NewsDto newsDto) {
        News news = conversionService.convert(newsDto, News.class);
        news = newsRepository.save(news);
        return conversionService.convert(news, NewsDto.class);
    }

    @Override
    public void deleteNews(int id) {
        News news = newsRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Новости с таким ID не существует"));
        news.setDeleted(true);

        newsRepository.save(news);
    }
}
