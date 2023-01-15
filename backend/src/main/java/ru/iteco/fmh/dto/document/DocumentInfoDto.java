package ru.iteco.fmh.dto.document;

import io.swagger.v3.oas.annotations.media.Schema;

public class DocumentInfoDto {

    @Schema(name = "id", description = "Идентификатор документа")
    Integer id;

    @Schema(name = "filePath", description = "Полная ссылка на документ")
    String filePath;

    @Schema(name = "description", description = "Описание")
    String description;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
