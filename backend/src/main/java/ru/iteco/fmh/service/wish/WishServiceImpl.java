package ru.iteco.fmh.service.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.Util;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishCommentInfoDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishPaginationDto;
import ru.iteco.fmh.dto.wish.WishVisibilityDto;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;
import static ru.iteco.fmh.model.task.Status.CANCELLED;
import static ru.iteco.fmh.model.task.Status.IN_PROGRESS;
import static ru.iteco.fmh.model.task.Status.OPEN;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final WishCommentRepository wishCommentRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public WishPaginationDto getWishes(int pages, int elements, List<Status> status, boolean planExecuteDate) {
        String currentUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();

        List<String> currentUserRoleNamesList = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Pageable pageableList = planExecuteDate
                ? PageRequest.of(pages, elements, Sort.by("planExecuteDate").and(Sort.by("createDate").descending()))
                : PageRequest.of(pages, elements, Sort.by("createDate").descending());

        Page<Wish> list = (status == null || status.isEmpty())
                ? wishRepository.findAllByCurrentRoles(List.of(OPEN, IN_PROGRESS), currentUserRoleNamesList, currentUserLogin, pageableList)
                : wishRepository.findAllByCurrentRoles(status, currentUserRoleNamesList, currentUserLogin, pageableList);

        return WishPaginationDto.builder()
                .pages(list.getTotalPages() - 1)
                .elements(
                        list.stream()
                                .map(i -> conversionService.convert(i, WishDto.class))
                                .collect(Collectors.toList()))
                .build();
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
    public WishDto createWish(WishDto wishDto) {
        List<Role> roleList = roleRepository.findAllByIdIn(wishDto.getWishVisibility());
        wishDto.setStatus(wishDto.getExecutor() == null ? OPEN : IN_PROGRESS);
        Wish wish = conversionService.convert(wishDto, Wish.class);
        wish.setWishRoles(roleList);
        wish = wishRepository.save(wish);
        return conversionService.convert(wish, WishDto.class);
    }

    @Override
    public WishDto getWish(int wishId) {
        Wish wish = wishRepository
                .findById(wishId)
                .orElseThrow(() -> new IllegalArgumentException("Просьбы с таким ID не существует"));
        return conversionService.convert(wish, WishDto.class);
    }

    @Transactional
    @Override
    public WishDto updateWish(WishDto wishDto, Authentication authentication) {
        List<Role> roleList = roleRepository.findAllByIdIn(wishDto.getWishVisibility());
        User userCreator = userRepository.findUserById(wishDto.getCreatorId());
        Util util = new Util(userRepository);
        util.checkUpdatePossibility(userCreator, authentication);
        wishDto.setStatus(wishDto.getExecutor() == null ? OPEN : IN_PROGRESS);
        Wish wish = conversionService.convert(wishDto, Wish.class);
        wish.setWishRoles(roleList);
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
                new IllegalArgumentException("Просьбы с таким ID не существует"));
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
                    new IllegalArgumentException("User does not exist!"));
        }
        wish.changeStatus(status, executor);
        wish = wishRepository.save(wish);
        return conversionService.convert(wish, WishDto.class);
    }


    @Override
    public WishCommentInfoDto getWishComment(int commentId) {
        WishComment wishComment = wishCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Комментария с таким ID не существует"));
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
        WishComment wishComment = conversionService.convert(wishCommentDto, WishComment.class);
        wishComment.setWish(wishRepository.findById(wishId)
                .orElseThrow(() -> new IllegalArgumentException("Просьбы с таким ID не существует")));
        wishComment = wishCommentRepository.save(wishComment);
        return  conversionService.convert(wishComment, WishCommentInfoDto.class);

    }

    @Transactional
    @Override
    public WishCommentInfoDto updateWishComment(WishCommentDto wishCommentDto, Authentication authentication) {
        User userCreator = userRepository.findUserById(wishCommentDto.getCreatorId());
        Util util = new Util(userRepository);
        util.checkUpdatePossibility(userCreator, authentication);
        WishComment wishComment = conversionService.convert(wishCommentDto, WishComment.class);
        wishComment = wishCommentRepository.save(wishComment);
        return conversionService.convert(wishComment, WishCommentInfoDto.class);
    }

    @Override
    public List<WishVisibilityDto> createWishVisibilityDtoList() {
        return roleRepository.findAll().stream().map(role -> WishVisibilityDto.builder()
                .id(role.getId()).name(role.getName()).build()).toList();
    }
}
