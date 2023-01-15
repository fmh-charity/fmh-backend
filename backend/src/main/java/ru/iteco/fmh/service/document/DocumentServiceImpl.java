package ru.iteco.fmh.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.DocumentRepository;
import ru.iteco.fmh.dto.document.DocumentInfoDto;
import ru.iteco.fmh.model.document.Document;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final ConversionService conversionService;

    @Override
    public List<DocumentInfoDto> getDocumentInfo(String host, List<DocumentStatus> status) {
        List<Document> documents = documentRepository.findAllByStatusInAndDeletedIsFalse(status);
        List<DocumentInfoDto> listDocumentInfoDto = documents.stream()
                .map(document -> conversionService.convert(document, DocumentInfoDto.class))
                .collect(Collectors.toList());

        listDocumentInfoDto.forEach(documentInfoDto -> documentInfoDto.setFilePath(host + "/" + documentInfoDto.getFilePath()));
        return listDocumentInfoDto;
    }
}
