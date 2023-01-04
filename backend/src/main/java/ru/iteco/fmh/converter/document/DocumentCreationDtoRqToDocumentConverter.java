package ru.iteco.fmh.converter.document;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.model.document.Document;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DocumentCreationDtoRqToDocumentConverter implements Converter<DocumentCreationDtoRq, Document> {

    @Override
    public Document convert(@NonNull DocumentCreationDtoRq source) {
        Document document = new Document();
        BeanUtils.copyProperties(source, document);
        document.setCreateDate(Instant.now());
        document.setStatus(DocumentStatus.NEW);
        document.setDeleted(false);
        System.out.println(document);
        return document;
    }
}
