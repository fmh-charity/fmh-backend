package ru.iteco.cucumber.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfoPaginationDto {
    @JsonProperty("pages")
    int pages;
    @JsonProperty("elements")
    List<DocumentInfoDto> elements;
}
