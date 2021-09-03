package ru.iteco.fmh.service.news;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.NewsRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ConversionService conversionService;

    @Override
    public List<NewsDto> getAllNews() {
        List<News> news = newsRepository
                .findAllByPublishDateLessThanEqualAndPublishEnabledIsTrueAndDeletedIsFalseOrderByPublishDateDesc(LocalDateTime.now());
        return news.stream()
                .map(v -> conversionService.convert(v, NewsDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public NewsDto getNews(int id) {
        News news = newsRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Новости с таким ID не существует"));
        return conversionService.convert(news, NewsDto.class);
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
