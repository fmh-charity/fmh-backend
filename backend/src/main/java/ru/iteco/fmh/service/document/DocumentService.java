package ru.iteco.fmh.service.document;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import ru.iteco.fmh.dto.document.DocumentByIdRs;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.dto.document.DocumentForAdminPaginationRs;
import ru.iteco.fmh.dto.document.DocumentInfoPaginationDto;
import ru.iteco.fmh.dto.document.UpdateDocumentRq;
import ru.iteco.fmh.dto.document.UpdateDocumentRs;
import ru.iteco.fmh.model.document.DocumentStatus;

/**
 * сервис для работы с документами
 */
public interface DocumentService {

    /**
     * возвращает список всех документов
     *
     * @param pages страница
     * @param elements количество элементов на странице
     * @param isAscendingNameSort true = сортировать по имени в восходящем порядке, false = сортировать по имени в нисходящем порядке
     * @param statuses статусы документов один или более
     * @return список информации о документах постранично
     */
    DocumentInfoPaginationDto getAllDocumentInfo(int pages, int elements, boolean isAscendingNameSort, Collection<DocumentStatus> statuses);

    /**
     * возвращает список информации документов (расширенной) для администратора
     *
     * @param pages страница
     * @param elements количество элементов на странице
     * @param isAscendingNameSort true = сортировать по имени в восходящем порядке, false = сортировать по имени в нисходящем порядке
     * @return список информации по документам для администратора постранично
     */
    DocumentForAdminPaginationRs getDocumentsForAdmin(int pages, int elements, boolean isAscendingNameSort);

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
     * удаление документа
     *
     * @param id документа
     */
    void deleteDocument(int id);

    /**
     * обновляет метаданные документа
     *
     * @param updateDocumentRq информация которую необходимо обновить в документе
     * @param id               документа
     * @return сущность
     */
    UpdateDocumentRs updateDocument(int id, UpdateDocumentRq updateDocumentRq);
}
