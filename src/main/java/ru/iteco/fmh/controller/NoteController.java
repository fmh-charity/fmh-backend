package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.NoteDto;
import ru.iteco.fmh.dto.NoteShortInfoDto;

import java.util.List;

@Api(description = "Работа с записками")
@RestController
@RequestMapping("/note")
public class NoteController {

    @ApiOperation(value = "реестр всех записок с учетом пагинации")
    @GetMapping
    public List<NoteShortInfoDto> getAllNotes(
            @ApiParam(value = "начальная позиция пагинации", required = true)@RequestParam("offset") Integer offset,
            @ApiParam(value = "конечная позиция пагинации", required = true)@RequestParam("limit") Integer limit,
            @ApiParam(value = "показывать только активные записки")@RequestParam("show_active") Boolean showActive
    ){
        return null;
    }

    @ApiOperation(value = "возвращает полную информацию по записке")
    @GetMapping("/{id}")
    public NoteDto getNote(
            @ApiParam(value = "идентификатор записки", required = true)@PathVariable("id") Integer id
    ){
        return null;
    }

    @ApiOperation(value = "создает новую записку, возвращает ид новой записки")
    @PostMapping
    public Integer createNote(
            @RequestBody NoteDto noteDto
    ){
        return null;
    }

    @ApiOperation(value = "обновляет информацию по записке")
    @PatchMapping
    public void updateNote(
            @RequestBody NoteDto noteDto
    ){
    }
}
