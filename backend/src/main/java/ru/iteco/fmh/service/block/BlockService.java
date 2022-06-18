package ru.iteco.fmh.service.block;

import ru.iteco.fmh.dto.block.BlockDtoRq;
import ru.iteco.fmh.dto.block.BlockDtoRs;

import java.util.List;

/**
 * сервис для работы с блоками
 */
public interface BlockService {
    /**
     * возвращает список всех блоков
     */
    List<BlockDtoRs> getAllBlocks();

    /**
     * возвращает блок для просмотра
     *
     * @param id ид блока
     * @return блок с полной информацией
     */
    BlockDtoRs getBlock(int id);

    /**
     * создает новый блок/обновляет блок
     *
     * @param blockDtoRq информация по блоку для обновления
     * @return сущность
     */
    BlockDtoRs createBlock(BlockDtoRq blockDtoRq);

    /**
     * удаление блока
     *
     * @param id ид блока
     */
    void deleteBlock(int id);

    BlockDtoRs updateBlock(BlockDtoRq dto, int id);
}
