package ru.iteco.fmh.service.document;

import ru.iteco.fmh.dto.document.DocumentInfoDto;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.util.List;

/**
 * сервис для работы с документами
 */
public interface DocumentService {
    /**
     * возвращает список всех документов
     */
    List<DocumentInfoDto> getDocumentInfo(String host, List<DocumentStatus> status);

}
