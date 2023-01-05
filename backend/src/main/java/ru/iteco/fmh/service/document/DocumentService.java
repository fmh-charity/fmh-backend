package ru.iteco.fmh.service.document;

import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;

/**
 * сервис для работы с документами
 */
public interface DocumentService {
    DocumentCreationDtoRs createDocument(DocumentCreationDtoRq documentCreationDtoRqq);
}
