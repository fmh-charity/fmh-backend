package ru.iteco.fmh.service.news;

import ru.iteco.fmh.dto.news.NewsResponseDto;

import java.util.List;

/**
 * сервис для работы с новостями
 */
public interface NewsService {
    /**
     * возвращает список всех новостей
     */
    List<NewsResponseDto> getAllNews();

    /**
     * создает новую новость
     *
     * @param newsResponseDto информация по новой новости
     * @return новость с полной информацией
     */
    NewsResponseDto createNews(NewsResponseDto newsResponseDto);

    /**
     * возвращает новость для просмотра
     *
     * @param id ид новости
     * @return новость с полной информацией
     */
    NewsResponseDto getNews(int id);

    /**
     * обновляет новость
     *
     * @param newsResponseDto информация по новости для обновления
     * @return обновленная сущность
     */
    NewsResponseDto updateNews(NewsResponseDto newsResponseDto);

    /**
     * удаление новости
     *
     * @param id ид новости
     */
     void deleteNews(int id);
}
