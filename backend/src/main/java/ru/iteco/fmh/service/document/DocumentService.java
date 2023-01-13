package ru.iteco.fmh.service.document;

import org.springframework.web.multipart.MultipartFile;

/**
 * сервис для работы с документами
 */
public interface DocumentService {
    /**
     * сохраняет документ в деректорию, возвращает путь хранения документа.
     *
     * @param multipartFile документ для загрузки
     * @return родительская деректория + имя файла
     */
    String uploadDocument(MultipartFile multipartFile);

    /**
     * удаление документа
     *
     * @param id документа
     */
    void deleteDocument(int id);
}
