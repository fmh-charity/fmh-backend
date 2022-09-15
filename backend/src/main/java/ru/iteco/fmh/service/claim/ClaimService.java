package ru.iteco.fmh.service.claim;

import org.springframework.security.core.Authentication;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.pagination.PageablePogo;
import ru.iteco.fmh.model.task.Status;

import java.util.List;

/**
 * сервис для работы с заявками
 */
public interface ClaimService {

    /**
     * возвращает список всех просьб
     */
    List<ClaimDto> getAllClaims();

    /**
     * возвращает полный перечень всех заявок
     *
     * @return список с полной информацией по заявкам
     */
    List<ClaimDto> getOpenInProgressClaims();

    /**
     * создает новую заявку
     *
     * @param claimDto информация по новой заявке
     * @return id заявки
     */
    ClaimDto createClaim(ClaimDto claimDto);

    /**
     * возвращает заявку для просмотра
     *
     * @param id ид заявки
     * @return заявка с полной информацией
     */
    ClaimDto getClaim(int id);

    /**
     * обновляет заявку
     *
     * @param claimDto информация по заявке для обновления
     * @return обновленная сущность
     */
    ClaimDto updateClaim(ClaimDto claimDto, Authentication authentication);

    /**
     * изменяет статус заявки на - исполнен, отменен
     *
     * @param claimId ид заявки
     * @param status  значение нового статуса для заявки
     * @return заявку с измененным статусом
     */
    ClaimDto changeStatus(int claimId, Status status, Integer executorId, ClaimCommentDto claimCommentDto);

    /**
     * добавляет комент к заявке
     *
     * @param claimId ид заявки
     * @return заявку с добпвленным коментом
     */
    ClaimCommentDto addComment(int claimId, ClaimCommentDto claimCommentDto);

    /**
     * возвращает комментарий заявки для просмотра
     *
     * @param claimCommentId ид комментария заявки
     * @return комментарий заявки
     */
    ClaimCommentDto getClaimComment(int claimCommentId);

    /**
     * возвращает список всех комментов к заявке
     */
    List<ClaimCommentDto> getAllClaimsComments(int claimId);

    /**
     * обновляет комментарий заявки
     *
     * @return обновленная сущность коммента
     */
    ClaimCommentDto updateClaimComment(ClaimCommentDto commentDto, Authentication authentication);

    /**
     * возвращает заданное количество просьб на указанной странице (значение page), с заданной сортировкой
     * @throws Exception
     */
    List<ClaimDto> getPaginationClaims(PageablePogo pageablePogo);
}
