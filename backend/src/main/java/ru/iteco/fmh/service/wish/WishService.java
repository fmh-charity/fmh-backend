package ru.iteco.fmh.service.wish;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishCommentInfoDto;
import ru.iteco.fmh.dto.wish.WishCreationRequest;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishPaginationDto;
import ru.iteco.fmh.dto.wish.WishUpdateRequest;
import ru.iteco.fmh.dto.wish.WishVisibilityDto;
import ru.iteco.fmh.model.wish.Status;

import java.util.List;

/**
 * сервис для работы с просьбами
 */
public interface WishService {
    /**
     * возвращает список всех просьб
     * @param pages количество страниц
     * @param elements количество елементов в странице
     * @param searchValue строка с элементом для фильтрации
     * @param sortField значение сортировки
     * @param sortDirection направление сортировки
     * @return список просьб с пагинацией
     */
    WishPaginationDto getWishes(int pages, int elements, String searchValue, String sortField, String sortDirection);

    /**
     * возвращает список всех просьб co статусом open/in_progress
     */
    List<WishDto> getOpenInProgressWishes();

    /**
     * возвращает просьбу для просмотра
     *
     * @param id ид просьбы
     * @return просьба с полной информацией
     */
    WishDto getWish(int id);

    /**
     * создает новую просьбу
     *
     * @param wishCreationRequest информация по новой просьбе
     * @return id просьбы
     */
    WishDto createWish(WishCreationRequest wishCreationRequest);

    /**
     * обновляет просьбу
     *
     * @param wishUpdateRequest информация по просьбе для обновления
     * @param wishId индентификатор просьбы которую нужно обновить
     * @return обновленная сущность просьбы
     */
    WishDto updateWish(WishUpdateRequest wishUpdateRequest, Authentication authentication, Integer wishId);

    /**
     * возвращает список всех просьб по пациенту
     *
     * @param patientId ид пациента
     * @return список всех просьб по пациенту
     */
    List<WishDto> getPatientAllWishes(int patientId);

    /**
     * возвращает список всех просьб по пациенту co статусом open/in_progress
     *
     * @param patientId ид пациента
     * @return список всех просьб по пациенту co статусом open/in_progress
     */
    List<WishDto> getPatientOpenInProgressWishes(int patientId);

    /**
     * изменяет статус просьбы - обработка документа “Просьба” по статусной модели
     *
     * @param wishId ид просьбы
     * @param status значение нового статуса для просьбы
     * @return просьбу с измененным статусом
     */
    WishDto changeStatus(int wishId, Status status, Integer executorId, WishCommentDto wishCommentDto);


    /**
     * возвращает комментарий для просмотра
     *
     * @param commentId ид комментария
     * @return комментарий с полной информацией
     */
    WishCommentInfoDto getWishComment(int commentId);

    /**
     * возвращает все комментарии просьбы для просмотра
     *
     * @param wishId ид просьбы
     * @return список всех комментариев по просьбе
     */
    List<WishCommentInfoDto> getAllWishComments(int wishId);

    /**
     * создает новый комментарий просьбы
     *
     * @param wishId ид просьбы
     * @param wishCommentDto информация по комментарию
     * @return id комментария
     */
    WishCommentInfoDto createWishComment(int wishId, WishCommentDto wishCommentDto);


    /**
     * обновляет комментарий просьбы
     *
     * @param wishCommentDto информация по комментарию для обновления
     * @return обновленная сущность комментария
     */
    WishCommentInfoDto updateWishComment(WishCommentDto wishCommentDto, Authentication authentication);

    /**
     * удаляет комментарий просьбы
     *
     * @param commentId идентификатор комментария
     */
    void deleteWishComment(int commentId);

    /**
     * создает список видимости просьбы из Role
     *
     * @return список видимости просьбы
     */
    List<WishVisibilityDto> createWishVisibilityDtoList();

    /**
     * меняет статус просьбы на canceled
     *
     * @param wishId ид просьбы
     * @return просьбу с измененным статусом
     */
    WishDto cancelWish(int wishId);

    /**
     * назначает исполнителя просьбе
     *
     * @param wishId ид просьбы
     * @return просьбу с назначенным исполнителем
     */
    WishDto joinWish(int wishId);

    /**
     * назначает исполнителя просьбе
     *
     * @param wishId ид просьбы
     * @return просьбу со статусом Ready
     */
    WishDto confirmWishExecution(int wishId);

    /**
     * меняет статус просьбы на IN_PROGRESS
     *
     * @param wishId ид просьбы
     * @return просьбу с измененным статусом
     */
    WishDto declineWishExecution(int wishId);

    /**
     * после исполнения просьбы менят статус на READY_CHECK
     *
     * @param wishId идентификатор просьбы
     * @return просьбу с измененным статусом
     */
    WishDto executeWish(int wishId);

    /**
     * удаляет текущего исполнителя просьбы
     *
     * @param wishId ид просьбы
     * @return просьбу с измененным списком исполнителей
     */
    WishDto detachFromWish(int wishId);

    /**
     * администратор удаляет исполнителя просьбы
     *
     * @param wishId ид просьбы
     * @param userId ид пользователя
     * @return просьбу с измененным списком исполнителей
     */
    WishDto detachExecutor(int wishId, int userId);
}
