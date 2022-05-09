package ru.iteco.fmh.converter.news;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.NewsCategoryRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class NewsDtoToNewsConverter implements Converter<NewsDto, News> {
    private final NewsCategoryRepository newsCategoryRepository;
    private final UserRepository userRepository;

    @Override
    public News convert(@NonNull NewsDto newsDto) {
        News news = new News();
        BeanUtils.copyProperties(newsDto, news);

        news.setNewsCategory(newsDto.getNewsCategoryId() != null
                ? newsCategoryRepository.findNewsCategoryById(newsDto.getNewsCategoryId()) : null);

        news.setCreator(newsDto.getCreatorId() != null
                ? userRepository.findUserById(newsDto.getCreatorId()) : null);

        news.setCreateDate(newsDto.getCreateDate() != null
                ? Instant.ofEpochMilli(newsDto.getCreateDate()) : null);

        news.setPublishDate(newsDto.getPublishDate() != null
                ? Instant.ofEpochMilli(newsDto.getPublishDate()) : null);

        return news;
    }
}
