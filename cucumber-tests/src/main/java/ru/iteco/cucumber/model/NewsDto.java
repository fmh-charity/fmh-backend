package ru.iteco.cucumber.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat
public class NewsDto {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("newsCategoryId")
    private Integer newsCategoryId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("creatorId")
    private Integer creatorId;
    @JsonProperty("createDate")
    private LocalDateTime createDate;
    @JsonProperty("publishDate")
    private LocalDateTime publishDate;
    @JsonProperty("publishEnabled")
    private boolean publishEnabled;
    @JsonProperty("creatorName")
    private String creatorName;

}
