package ru.iteco.fmh.service.block;

import ru.iteco.fmh.dto.block.BlockDto;

import java.util.List;

/**
 * сервис для работы с блоками
 */
public interface BlockService {
    /**
     * возвращает список всех блоков
     */
    List<BlockDto> getAllBlocks();

    /**
     * возвращает блок для просмотра
     *
     * @param id ид блока
     * @return блок с полной информацией
     */
    BlockDto getBlock(int id);

    /**
     * создает новый блок/обновляет блок
     *
     * @param blockDto информация по блоку для обновления
     * @return сущность
     */
    BlockDto createOrUpdateBlock(BlockDto blockDto);

    /**
     * удаление блока
     *
     * @param id ид блока
     */
    void deleteBlock(int id);
}
