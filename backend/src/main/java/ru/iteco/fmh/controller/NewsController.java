package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.news.NewsPaginationDto;
import ru.iteco.fmh.service.news.NewsService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

@Api(description = "новости")
@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
@Validated
public class NewsController {

    private final NewsService newsService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех новостей")
    @GetMapping
    public ResponseEntity<NewsPaginationDto> getNews(
            @ApiParam(required = false, name = "pages", value = "От 0")
            @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
            @ApiParam(required = false, name = "elements", value = "От 1 до 200")
            @RequestParam(defaultValue = "8") @Min(value = 1) @Max(value = 200) int elements,
            @ApiParam(required = false, name = "createDate", value = "Сортировка по дате исполнения")
            @RequestParam(defaultValue = "true") boolean publishDate,
            @ApiParam(required = false, name = "newsCategoryId", value = "Сортировка по новостной категории")
            @RequestParam() Integer newsCategoryId,
            @ApiParam(required = false, name = "publishDateFrom", value = "Выборка новостей от назначеной даты")
            @RequestParam() String publishDateFrom,
            @ApiParam(required = false, name = "publishDateTo", value = "Выборка новостей до назначеной даты")
            @RequestParam() String publishDateTo) {
        return ResponseEntity.ok(newsService.getNews(pages, elements, publishDate, newsCategoryId, publishDateFrom, publishDateTo));
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает полную информацию по новости")
    @GetMapping("/{id}")
    public NewsDto getNews(@ApiParam(value = "идентификатор новости", required = true) @PathVariable("id") int id) {
        return newsService.getNews(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "cоздание новой новости")
    @PostMapping
    public NewsDto createNews(@RequestBody NewsDto dto) {
        return newsService.createOrUpdateNews(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "обновляет информацию по новости")
    @PutMapping
    public NewsDto updateNews(@RequestBody NewsDto dto) {
        return newsService.createOrUpdateNews(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "удаление новости")
    @DeleteMapping("/{id}")
    public void deleteNews(@ApiParam(value = "идентификатор новости", required = true) @PathVariable("id") int id) {
        newsService.deleteNews(id);
    }
}
