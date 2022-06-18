package ru.iteco.fmh.converter.block;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.block.BlockDtoRs;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.Block;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class BlockToBlockRsConverter implements Converter<Block, BlockDtoRs> {

    @Override
    public BlockDtoRs convert(Block source) {
        return BlockDtoRs.builder()
                .name(source.getName())
                .comment(source.getComment())
                .id(source.getId())
                .build();
    }

    public List<BlockDtoRs> convert(List<Block> source) {
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

}
