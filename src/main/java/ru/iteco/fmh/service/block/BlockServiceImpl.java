package ru.iteco.fmh.service.block;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.BlockRepository;
import ru.iteco.fmh.dto.block.BlockDto;
import ru.iteco.fmh.model.Block;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final ConversionService conversionService;

    @Override
    public List<BlockDto> getAllBlocks() {
        return blockRepository.findAll().stream()
                .map(block -> conversionService.convert(block, BlockDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BlockDto getBlock(int id) {
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Блока с таким ID не существует"));
        return conversionService.convert(block, BlockDto.class);
    }

    @Override
    @Transactional
    public BlockDto createOrUpdateBlock(BlockDto blockDto) {
        Block block = conversionService.convert(blockDto, Block.class);
        return conversionService.convert(blockRepository.save(block), BlockDto.class);
    }

    @Override
    @Transactional
    public void deleteBlock(int id) {
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Блока с таким ID не существует"));
        block.setDeleted(true);
    }
}
