package ru.iteco.fmh.converter.claim.fromClaimDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import ru.iteco.fmh.converter.user.fromUserDto.IUserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.claim.Claim;

import ru.iteco.fmh.model.user.User;



public class ClaimDtoToClaimConverter implements Converter<ClaimDto, Claim> {

    private final IUserDtoToUserConverter userDtoToUserConverter;

    public ClaimDtoToClaimConverter(IUserDtoToUserConverter userDtoToUserConverter) {
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

    @Override
    public Claim convert(ClaimDto dto) {
        Claim claim = new Claim();
        if (dto.getExecutor() == null) {
            //делаем Mock и ставим вместо executor = null
            dto.setExecutor(getUserDto());
            BeanUtils.copyProperties(dto, claim);
            User creator = userDtoToUserConverter.convert(dto.getCreator());
            //убираем mock и ставим null обратно
            claim.setExecutor(null);
            claim.setCreator(creator);
        } else {
            BeanUtils.copyProperties(dto, claim);
            User executor = userDtoToUserConverter.convert(dto.getExecutor());
            User creator = userDtoToUserConverter.convert(dto.getCreator());
            claim.setExecutor(executor);
            claim.setCreator(creator);

        }
        return claim;
    }


    public static UserDto getUserDto() {
        return UserDto.builder()
                .id(Integer.valueOf(2))
                .build();

    }
}
