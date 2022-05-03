package ru.iteco.fmh.service.post;

import ru.iteco.fmh.dto.post.PostDto;

import java.util.List;

/**
 * Сервис для работы с постами
 */

public interface PostService {

    /**
     * Возвращает список всех постов
     */
    List<PostDto> getAll();

    /**
     * Создание нового поста / обновление поста
     *
     * @param postDto - новый или измененный объект поста
     * @return - новый или измененный объект поста
     */
    PostDto createOrUpdatePost(PostDto postDto);

    /**
     * Просмотр карточки поста
     *
     * @param - ID поста
     * @return - объект поста
     */
    PostDto getPost(int id);

    /**
     * Удаление поста
     *
     * @param id - ID удаляемого поста
     */
    void deletePost(int id);

}
