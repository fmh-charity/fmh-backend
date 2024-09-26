package ru.iteco.fmh.converter.document;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.UserToUserDtoIdConcatFioConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dto.document.DocumentForAdminRs;
import ru.iteco.fmh.dto.user.UserDtoIdConcatFio;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.document.Document;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class DocumentToDocumentForAdminRsConverter implements Converter<Document, DocumentForAdminRs> {
    private final UserToUserDtoIdConcatFioConverter userToUserDtoIdConcatFioConverter;
    @Value("${static-host.host}")
    private String staticHost;

    @SneakyThrows
    @Override
    public DocumentForAdminRs convert(Document document) {
        UserDtoIdConcatFio userDtoIdConcatFio = userToUserDtoIdConcatFioConverter.convert(document.getUser());
        return DocumentForAdminRs.builder()
                .id(document.getId())
                .name(document.getName())
                .filePath(new URI(staticHost + document.getFilePath()).normalize().toString())
                .description(document.getDescription())
                .status(document.getStatus())
                .createDate(document.getCreateDate())
                .user(userDtoIdConcatFio)
                .build();
    }
}
