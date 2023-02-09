package ru.iteco.fmh.converter.document;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dto.document.DocumentByIdRs;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.document.Document;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class DocumentToDocumentByIdRsConverter implements Converter<Document, DocumentByIdRs> {
    private final UserToUserDtoIdFioConverter userToUserDtoIdFioConverter;
    @Value("${vhospise-app.static-host}")
    private String staticHost;

    @SneakyThrows
    @Override
    public DocumentByIdRs convert(Document document) {
        UserDtoIdFio userDtoIdFio = userToUserDtoIdFioConverter.convert(document.getUser());
        return DocumentByIdRs.builder()
                .id(document.getId())
                .name(document.getName())
                .filePath(new URI(staticHost + document.getFilePath()).normalize().toString())
                .description(document.getDescription())
                .deleted(document.isDeleted())
                .status(document.getStatus())
                .createDate(document.getCreateDate())
                .user(userDtoIdFio)
                .build();
    }
}
