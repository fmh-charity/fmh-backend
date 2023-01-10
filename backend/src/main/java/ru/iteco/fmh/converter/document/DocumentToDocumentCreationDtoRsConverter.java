package ru.iteco.fmh.converter.document;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.document.Document;

@Component
@RequiredArgsConstructor
public class DocumentToDocumentCreationDtoRsConverter implements Converter<Document, DocumentCreationDtoRs> {
    private final UserToUserDtoIdFioConverter conversionService;
    @Value("${static-host.host}")
    private String staticHost;

    @Override
    public DocumentCreationDtoRs convert(Document source) {
        UserDtoIdFio userDtoIdFio = conversionService.convert(source.getUser());
        return DocumentCreationDtoRs.builder()
                .id(source.getId())
                .name(source.getName())
                .filePath(staticHost +source.getFilePath())
                .description(source.getDescription())
                .status(source.getStatus())
                .createDate(source.getCreateDate())
                .user(userDtoIdFio)
                .build();

    }
}
