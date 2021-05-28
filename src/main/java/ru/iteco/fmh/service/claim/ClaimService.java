package ru.iteco.fmh.service.claim;
import ru.iteco.fmh.dto.claim.ClaimDto;

import java.util.List;

/**
* сервис для работы с заявками
*/
public interface ClaimService {
    /**
     * возвращает полный перечень всех заявок
     * @return список с полной инфой по заявкам
     */
    List<ClaimDto> getClaims();

    /**
     * создает новую заявку
     * @param claimDto информация по новой заявке
     * @return заявку с полной инфой
     */
    ClaimDto createClaim(ClaimDto claimDto);

    /**
     * возвращает заявку для просмотра
     * @param id ид заявки
     * @return заявка с полной инфой
     */
    ClaimDto getClaim(Integer id);
}
