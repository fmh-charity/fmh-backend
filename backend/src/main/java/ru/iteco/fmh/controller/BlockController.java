package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.iteco.fmh.dto.block.BlockDtoRq;
import ru.iteco.fmh.dto.block.BlockDtoRs;
import ru.iteco.fmh.service.block.BlockService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api("Блоки")
@RequiredArgsConstructor
@RestController
@RequestMapping("/blocks")
public class BlockController {

    private final BlockService blockService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "получние всех блоков")
    @GetMapping
    public List<BlockDtoRs> getAllBlocks() {
        return blockService.getAllBlocks();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает полную информацию по блоку")
    @GetMapping("/{id}")
    public BlockDtoRs getBlock(@ApiParam(value = "идентификатор блока", required = true) @PathVariable("id") int id) {
        return blockService.getBlock(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "cоздание нового блока")
    @PostMapping
    public BlockDtoRs createBlock(@RequestBody BlockDtoRq dto) {
        return blockService.createBlock(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "обновляет информацию по блоку")
    @PutMapping("/{id}")
    public BlockDtoRs updateBlock(@RequestBody BlockDtoRq dto, @NotEmpty @PathVariable(value = "id") int id) {
        return blockService.updateBlock(dto, id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "удаление блока")
    @DeleteMapping("/{id}")
    public void deleteBlock(@ApiParam(value = "идентификатор блока", required = true) @PathVariable("id") int id) {
        blockService.deleteBlock(id);
    }
}
