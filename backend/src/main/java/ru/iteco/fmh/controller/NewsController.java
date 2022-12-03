package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.news.NewsPaginationDto;
import ru.iteco.fmh.service.news.NewsService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

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
            @ApiParam(required = false, name = "publishDate", value = "Сортировка по дате исполнения")
            @RequestParam(required = true) boolean publishDate,
            @ApiParam(required = false, name = "newsCategoryId", value = "Фильтрация по категории")
            @RequestParam(required = false) Integer newsCategoryId,
            @ApiParam(required = false, name = "publishDateFrom", value = "Выборка новостей от назначеной даты")
            @RequestParam(required = false) LocalDate publishDateFrom,
            @ApiParam(required = false, name = "publishDateTo", value = "Выборка новостей до назначеной даты")
            @RequestParam(required = false) LocalDate publishDateTo) {
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
        return newsService.createNews(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "обновляет информацию по новости")
    @PutMapping
    public NewsDto updateNews(@RequestBody NewsDto dto) {
        return newsService.updateNews(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "удаление новости")
    @DeleteMapping("/{id}")
    public void deleteNews(@ApiParam(value = "идентификатор новости", required = true) @PathVariable("id") int id) {
        newsService.deleteNews(id);
    }
}
