package ru.iteco.fmh.service.claim;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimShortInfoDto;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.model.StatusE;

import java.util.List;

/**
* сервис для работы с заявками
*/
public interface ClaimService {
    /**
     * возвращает полный перечень всех заявок
     * @return список с полной информацией по заявкам
     */
    List<ClaimShortInfoDto> getAllClaims();

    /**
     * создает новую заявку
     * @param claimDto информация по новой заявке
     * @return id заявки
     */
    Integer createClaim(ClaimDto claimDto);

    /**
     * возвращает заявку для просмотра
     * @param id ид заявки
     * @return заявка с полной информацией
     */
    ClaimDto getClaim(Integer id);

    /**
     * обновляет заявку
     * @param claimDto информация по заявке для обновления
     * @return обновленная сущность
     */
    ClaimDto updateClaim(ClaimDto claimDto);

    /**
     * изменяет статус заявки на - исполнен, отменен
     * @param claimId ид заявки
     * @param status значение нового статуса для заявки
     * @return заявку с измененным статусом
     */
    ClaimDto changeStatus (Integer claimId, StatusE status);
}
