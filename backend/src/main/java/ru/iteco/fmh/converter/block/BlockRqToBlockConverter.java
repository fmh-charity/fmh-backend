package ru.iteco.fmh.converter.block;

import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.block.BlockDtoRq;
import ru.iteco.fmh.model.Block;
import org.springframework.core.convert.converter.Converter;


@Component
public class BlockRqToBlockConverter implements Converter<BlockDtoRq, Block> {


    @Override
    public Block convert(BlockDtoRq source) {
        return Block.builder()
                .name(source.getName())
                .comment(source.getComment())
                .build();
    }

}
