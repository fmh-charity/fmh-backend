package ru.iteco.fmh.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConversionService conversionService;


    @Override
    public List<UserShortInfoDto> getAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream()
                .map(i -> conversionService.convert(i, UserShortInfoDto.class))
                .collect(Collectors.toList());
    }
}
