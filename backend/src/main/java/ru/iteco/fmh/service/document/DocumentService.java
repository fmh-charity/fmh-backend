package ru.iteco.fmh.service.document;

import org.springframework.web.multipart.MultipartFile;
import ru.iteco.fmh.dto.document.DocumentByIdRs;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.dto.document.DocumentForAdminRs;

import java.util.List;
import ru.iteco.fmh.dto.document.UpdateDocumentRq;
import ru.iteco.fmh.dto.document.UpdateDocumentRs;
import ru.iteco.fmh.dto.news.NewsDto;

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
     * создает новый документ
     *
     * @param documentCreationDtoRqq информация по документу для создания
     * @return сущность
     */
    DocumentCreationDtoRs createDocument(DocumentCreationDtoRq documentCreationDtoRqq);

    /**
     * возвращает документ для просмотра
     *
     * @param id ид документа
     * @return документ с полной информацией
     */
    DocumentByIdRs getDocument(int id);

    /**
     * возвращает список документов для администратора
     */
    List<DocumentForAdminRs> getDocumentsForAdmin();

    /**
     * удаление документа
     *
     * @param id документа
     */
    void deleteDocument(int id);

    /**
     * бновляет метаданные документа
     *
     * @param updateDocumentRq информация которую необходимо обновить в документе
     * @param id               документа
     * @return сущность
     */
    UpdateDocumentRs updateDocument(int id, UpdateDocumentRq updateDocumentRq);
}
