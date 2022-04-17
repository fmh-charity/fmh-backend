package ru.iteco.cucumber.model;

import io.cucumber.java.eo.Se;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewsDto {

    private Integer id;
    private Integer newsCategoryId;
    private String title;
    private String description;
    private Integer creatorId;
    private Long createDate;
    private Long publishDate;
    private boolean publishEnabled;
    private String creatorName;

}
