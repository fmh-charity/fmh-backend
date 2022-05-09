package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.block.BlockDto;
import ru.iteco.fmh.service.block.BlockService;

import java.util.List;

@Api(description = "Блоки")
@RequiredArgsConstructor
@RestController
@RequestMapping("/blocks")
public class BlockController {

    private final BlockService blockService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "получние всех блоков")
    @GetMapping
    public List<BlockDto> getAllBlocks() {
        return blockService.getAllBlocks();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает полную информацию по блоку")
    @GetMapping("/{id}")
    public BlockDto getBlock(@ApiParam(value = "идентификатор блока", required = true) @PathVariable("id") int id) {
        return blockService.getBlock(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "cоздание нового блока")
    @PostMapping
    public BlockDto createBlock(@RequestBody BlockDto dto) {
        return blockService.createOrUpdateBlock(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "обновляет информацию по блоку")
    @PutMapping
    public BlockDto updateBlock(@RequestBody BlockDto dto) {
        return blockService.createOrUpdateBlock(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "удаление блока")
    @DeleteMapping("/{id}")
    public void deleteBlock(@ApiParam(value = "идентификатор блока", required = true) @PathVariable("id") int id) {
        blockService.deleteBlock(id);
    }
}
