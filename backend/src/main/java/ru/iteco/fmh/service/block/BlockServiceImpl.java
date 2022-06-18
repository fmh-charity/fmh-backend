package ru.iteco.fmh.service.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.iteco.fmh.converter.block.BlockRqToBlockConverter;
import ru.iteco.fmh.converter.block.BlockToBlockRsConverter;
import ru.iteco.fmh.converter.room.RoomRqToRoomConverter;
import ru.iteco.fmh.dao.repository.BlockRepository;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.block.BlockDtoRq;
import ru.iteco.fmh.dto.block.BlockDtoRs;
import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.model.Block;
import ru.iteco.fmh.model.Room;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final BlockRqToBlockConverter blockRqToBlockConverter;
    private final BlockToBlockRsConverter blockToBlockRsConverter;
    private final RoomRqToRoomConverter roomRqToRoomConverter;

    private final RoomRepository roomRepository;

    @Override
    public List<BlockDtoRs> getAllBlocks() {
        return blockToBlockRsConverter.convert(blockRepository.findAll());
    }

    @Override
    public BlockDtoRs getBlock(int id) {
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Блока с таким ID не существует"));
        return blockToBlockRsConverter.convert(block);
    }

    @Override
    @Transactional
    public BlockDtoRs updateBlock(BlockDtoRq blockDtoRq, int id) {
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найдена сущность с id = " + id));
        if (StringUtils.hasText(blockDtoRq.getName())) {
            block.setName(blockDtoRq.getName());
        }
        if (StringUtils.hasText(blockDtoRq.getName())) {
            block.setComment(blockDtoRq.getComment());
        }
        if (!CollectionUtils.isEmpty(blockDtoRq.getRoomDtoRqSet())) {
            for (RoomDtoRq roomDtoRq : blockDtoRq.getRoomDtoRqSet()) {
                roomRepository.save(roomRqToRoomConverter.convert(roomDtoRq).toBuilder().block(block).build());
            }
        }
        return blockToBlockRsConverter.convert(blockRepository.save(block));
    }

    @Override
    @Transactional
    public BlockDtoRs createBlock(BlockDtoRq blockDtoRq) {
        if (blockRepository.findBlockByNameAndDeletedFalse(blockDtoRq.getName()).isPresent()) {
            throw new IllegalArgumentException("Сущность с name = " + blockDtoRq.getName() + " уже существует.");
        }
        Block block = blockRepository.save(blockRqToBlockConverter.convert(blockDtoRq));
        if (!CollectionUtils.isEmpty(blockDtoRq.getRoomDtoRqSet())) {
            for (RoomDtoRq roomDtoRq : blockDtoRq.getRoomDtoRqSet()) {
                if (roomDtoRq.getId() != null) {
                    Room room = roomRepository.findById(roomDtoRq.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Не найдена палата с id = " + roomDtoRq.getId()));
                    room.setBlock(block);
                    roomRepository.save(room);
                } else {
                    if (roomRepository.findRoomByNameAndDeletedFalse(roomDtoRq.getName()).isPresent()) {
                        throw new IllegalArgumentException("Room with name = " + roomDtoRq.getName() + " уже существует.");
                    }
                    roomRepository.save(Room.builder()
                            .name(roomDtoRq.getName())
                            .comment(roomDtoRq.getComment())
                            .maxOccupancy(roomDtoRq.getMaxOccupancy())
                            .block(block)
                            .build());
                }
            }
        }

        return blockToBlockRsConverter.convert(block);
    }

    @Override
    @Transactional
    public void deleteBlock(int id) {
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Блока с таким ID не существует"));
        block.setDeleted(true);
    }
}
