package ru.iteco.fmh.service.document;

import org.springframework.web.multipart.MultipartFile;
import ru.iteco.fmh.dto.document.DocumentByIdRs;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.dto.document.DocumentForAdminRs;

import java.util.List;

import ru.iteco.fmh.dto.document.DocumentInfoDto;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.util.List;

/**
 * сервис для работы с документами
 */
public interface DocumentService {

    /**
     * возвращает список всех документов
     *
     * @param statuses список статусов документов
     * @return список всех документов
     */
    List<DocumentInfoDto> getDocumentInfo(List<DocumentStatus> statuses);

    /**
     * сохраняет документ в деректорию, возвращает путь хранения документа.
     *
     * @param multipartFile документ для загрузки
     * @return родительская деректория + имя файла
     */
    String uploadDocument(MultipartFile multipartFile);

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
}
