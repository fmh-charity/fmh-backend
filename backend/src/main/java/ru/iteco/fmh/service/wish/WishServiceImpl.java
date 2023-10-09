package ru.iteco.fmh.service.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.Util;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishCommentInfoDto;
import ru.iteco.fmh.dto.wish.WishCreationRequest;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishUpdateRequest;
import ru.iteco.fmh.dto.wish.WishVisibilityDto;
import ru.iteco.fmh.exceptions.IncorrectDataException;
import ru.iteco.fmh.exceptions.NoRightsException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.exceptions.PermissionDeniedException;
import ru.iteco.fmh.exceptions.UnavailableOperationException;
import ru.iteco.fmh.exceptions.UserExistsException;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.model.wish.Status;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.wish.WishComment;
import ru.iteco.fmh.model.wish.WishExecutor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.List.of;
import static ru.iteco.fmh.model.wish.Status.CANCELLED;
import static ru.iteco.fmh.model.wish.Status.IN_PROGRESS;
import static ru.iteco.fmh.model.wish.Status.OPEN;
import static ru.iteco.fmh.model.wish.Status.READY;
import static ru.iteco.fmh.model.wish.Status.READY_CHECK;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final WishCommentRepository wishCommentRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<WishDto> getWishes(PageRequest pageRequest, String searchValue) {
        User currentLoggedInUser = Util.getCurrentLoggedInUser();
        String currentUserLogin = currentLoggedInUser.getLogin();
        List<String> currentUserRoleNamesList = currentLoggedInUser.getUserRoles().stream().map(Role::getName).toList();

        return wishRepository.findAllBySearchValue(currentUserRoleNamesList, currentUserLogin, searchValue, pageRequest)
                .stream()
                .map(i -> conversionService.convert(i, WishDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<WishDto> getOpenInProgressWishes() {
        List<Wish> list = wishRepository
                .findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(of(OPEN, IN_PROGRESS));
        return list.stream()
                .map(i -> conversionService.convert(i, WishDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public WishDto createWish(WishCreationRequest wishCreationRequest) {
        List<Role> roleList = roleRepository.findAllByIdIn(wishCreationRequest.getWishVisibility());
        Wish wish = conversionService.convert(wishCreationRequest, Wish.class);
        wish.setWishRoles(roleList);
        wish.setPatient(patientRepository.findPatientById(wishCreationRequest.getPatientId()));
        wish.setCreator(Util.getCurrentLoggedInUser());
        wish.setCreateDate(Instant.now());
        wish.setHelpRequest(false);
        wish.setExecutors(Collections.emptySet());
        wish = wishRepository.save(wish);
        return conversionService.convert(wish, WishDto.class);
    }

    @Override
    public WishDto getWish(int wishId) {
        Wish wish = wishRepository
                .findById(wishId)
                .orElseThrow(() -> new NotFoundException("Просьбы с таким ID не существует"));
        return conversionService.convert(wish, WishDto.class);
    }

    @Transactional
    @Override
    public WishDto updateWish(WishUpdateRequest wishUpdateRequest, Authentication authentication, Integer id) {
        Wish wish = wishRepository.findWishById(id);
        if (!wish.getStatus().equals(OPEN)) {
            throw new IncorrectDataException("Редактировать просьбу можно только в статусе Открыта");
        }
        User userCreator = wish.getCreator();
        Util.checkUpdatePossibility(userCreator);
        wish.setPatient(patientRepository.findPatientById(wishUpdateRequest.getPatientId()));
        wish.setTitle(wishUpdateRequest.getTitle());
        wish.setDescription(wishUpdateRequest.getDescription());
        wish.setPlanExecuteDate(wishUpdateRequest.getPlanExecuteDate() == null
                ? null : wishUpdateRequest.getPlanExecuteDate());
        wish.setHelpRequest(wishUpdateRequest.getHelpRequest());
        wish.setWishRoles(roleRepository.findAllByIdIn(wishUpdateRequest.getWishVisibility()));
        wish = wishRepository.save(wish);
        return conversionService.convert(wish, WishDto.class);
    }

    @Override
    public List<WishDto> getPatientAllWishes(int patientId) {
        return wishRepository
                .findAllByPatient_IdAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(patientId)
                .stream()
                .map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WishDto> getPatientOpenInProgressWishes(int patientId) {
        return wishRepository
                .findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(
                        patientId, of(OPEN, IN_PROGRESS)
                )
                .stream()
                .map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public WishDto changeStatus(int wishId, Status status, Integer executorId, WishCommentDto wishCommentDto) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(() ->
                new NotFoundException("Просьбы с таким ID не существует"));
        if (wish.getStatus() == IN_PROGRESS && status != CANCELLED) {
            if (!wishCommentDto.getDescription().equals("")) {
                createWishComment(wishId, wishCommentDto);
            } else {
                throw new IllegalArgumentException("Комментарий не может быть пустым!");
            }
        }
        User executor = new User();
        if (executorId != null) {
            executor = userRepository.findById(executorId).orElseThrow(() ->
                    new NotFoundException("User does not exist!"));
        }
        wish.changeStatus(status, executor);
        wish = wishRepository.save(wish);
        return conversionService.convert(wish, WishDto.class);
    }


    @Override
    public WishCommentInfoDto getWishComment(int commentId) {
        WishComment wishComment = wishCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментария с таким ID не существует"));
        return conversionService.convert(wishComment, WishCommentInfoDto.class);

    }

    @Override
    public List<WishCommentInfoDto> getAllWishComments(int wishId) {
        List<WishComment> wishCommentList = wishCommentRepository.findAllByWish_Id(wishId);
        return wishCommentList.stream().map(i -> conversionService.convert(i, WishCommentInfoDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public WishCommentInfoDto createWishComment(int wishId, WishCommentDto wishCommentDto) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(() -> new NotFoundException("Просьбы с таким ID не существует"));
        User user = Util.getCurrentLoggedInUser();
        WishComment wishComment = WishComment.builder().wish(wish)
                .creator(user).createDate(Instant.now()).description(wishCommentDto.getDescription()).build();
        wishComment = wishCommentRepository.save(wishComment);
        return conversionService.convert(wishComment, WishCommentInfoDto.class);
    }

    @Transactional
    @Override
    public WishCommentInfoDto updateWishComment(WishCommentDto wishCommentDto, Authentication authentication) {
        User userCreator = userRepository.findUserById(wishCommentDto.getCreatorId());
        Util.checkUpdatePossibility(userCreator);
        WishComment wishComment = conversionService.convert(wishCommentDto, WishComment.class);
        wishComment = wishCommentRepository.save(wishComment);
        return conversionService.convert(wishComment, WishCommentInfoDto.class);
    }

    @Override
    public void deleteWishComment(int commentId) {
        WishComment wishComment = wishCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с указанным идентификатором отсутствует"));
        Util.checkUpdatePossibility(wishComment.getCreator());
        wishCommentRepository.deleteById(commentId);
    }

    @Override
    public List<WishVisibilityDto> createWishVisibilityDtoList() {
        return roleRepository.findAll().stream().map(role -> WishVisibilityDto.builder()
                .id(role.getId()).name(role.getName()).build()).toList();
    }

    @Override
    public WishDto cancelWish(int wishId) {
        Wish foundWish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Просьба с указанным идентификатором отсутствует"));
        User currentUser = Util.getCurrentLoggedInUser();
        if (foundWish.getCreator().equals(currentUser) || Util.isAdmin(currentUser)) {
            foundWish.setStatus(CANCELLED);
        } else {
            throw new NoRightsException("Отменить просьбу может только создатель просьбы или администратор");
        }
        Wish updatedWish = wishRepository.save(foundWish);
        return conversionService.convert(updatedWish, WishDto.class);
    }

    @Override
    public WishDto joinWish(int wishId) {
        User currentUser = Util.getCurrentLoggedInUser();

        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Просьба с указанным идентификатором отсутствует"));

        boolean isUserExecutorAndFinishDateNull = wish.getExecutors().stream()
                .anyMatch(wishExecutor -> wishExecutor.getExecutor().equals(currentUser) && wishExecutor.getFinishDate() == null);

        if (isUserExecutorAndFinishDateNull) {
            throw new UserExistsException("Пользователь уже является испольнителем этой просьбы");
        }

        if (wish.getFactExecuteDate() != null) {
            throw new UnavailableOperationException("Невозможно присоедениться к выполненной просьбе");
        }

        wish.getExecutors().add(createExecutor(currentUser, wish));
        wish.setHelpRequest(false);
        Wish updatedWish = wishRepository.save(wish);

        return conversionService.convert(updatedWish, WishDto.class);
    }

    private WishExecutor createExecutor(User executor, Wish wish) {
        return WishExecutor.builder().wish(wish).executor(executor).joinDate(Instant.now()).build();
    }

    @Override
    public WishDto confirmWishExecution(int wishId) {
        Wish foundWish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Просьба с указанным идентификатором отсутствует"));

        foundWish.getExecutors().forEach(wishExecutor -> wishExecutor.setFinishDate(Instant.now()));
        foundWish.setFactExecuteDate(Instant.now());
        foundWish.setStatus(READY);
        Wish updatedWish = wishRepository.save(foundWish);

        return conversionService.convert(updatedWish, WishDto.class);
    }

    @Override
    public WishDto declineWishExecution(int wishId) {
        Wish foundWish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Просьба с указанным идентификатором отсутствует"));
        foundWish.setStatus(IN_PROGRESS);
        Wish updatedWish = wishRepository.save(foundWish);
        return conversionService.convert(updatedWish, WishDto.class);
    }

    @Override
    @Transactional
    public WishDto executeWish(int wishId) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(() ->
                new NotFoundException("Просьбы с таким ID не существует"));
        User executionInitiator = Util.getCurrentLoggedInUser();
        if (wish.getExecutors().stream()
                .noneMatch(el -> Objects.equals(el.getExecutor().getId(), executionInitiator.getId()))) {
            throw new PermissionDeniedException("Текущий пользователь отсутствует в списке исполнителей просьбы");
        }
        wish.setExecutionInitiator(executionInitiator);
        wish.setStatus(READY_CHECK);
        Wish updatedWish = wishRepository.save(wish);
        return conversionService.convert(updatedWish, WishDto.class);
    }

    @Override
    public WishDto detachFromWish(int wishId) {
        Wish foundWish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Просьба с указанным идентификатором отсутствует"));
        int userId = Util.getCurrentLoggedInUser().getId();
        return deleteWishExecutorById(userId, foundWish);
    }

    @Override
    public WishDto detachExecutor(int wishId, int userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с указанным идентификатором отсутствует");
        }
        Wish foundWish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Просьба с указанным идентификатором отсутствует"));
        return deleteWishExecutorById(userId, foundWish);
    }

    private WishDto deleteWishExecutorById(int userId, Wish wish) {
        WishExecutor wishExecutor = wish.getExecutors()
                .stream().filter(el -> Objects.equals(el.getExecutor().getId(), userId)
                        && wish.getFactExecuteDate() == null).findFirst()
                .orElseThrow(() -> new IncorrectDataException("Пользователь не найден в списке исполнителей,или просьбы выполнена"));
        wish.getExecutors().remove(wishExecutor);
        Wish updatedWish = wishRepository.save(wish);
        return conversionService.convert(updatedWish, WishDto.class);
    }
}
