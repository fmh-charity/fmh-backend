package ru.iteco.fmh.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.room.RoomDto;
import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.model.Room;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ConversionService conversionService;

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAllByDeletedIsFalse();
        return rooms.stream()
                .map(i -> conversionService.convert(i, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RoomDtoRs createOrUpdateRoom(int id, RoomDtoRq roomDto) {
        Room room = conversionService.convert(roomDto, Room.class);
        room = roomRepository.save(Objects.requireNonNull(room));
        return conversionService.convert(room, RoomDtoRs.class);
    }

    @Override
    public RoomDto getRoom(int id) {
        Room room = roomRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Палаты с таким ID не существует"));
        return conversionService.convert(room, RoomDto.class);
    }

    @Override
    public void deleteRoom(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Палаты с таким ID не существует"));
        room.setDeleted(true);
        roomRepository.save(room);
    }
}
