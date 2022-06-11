package ru.iteco.fmh.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.dto.room.RoomDtoRs;
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
    public List<RoomDtoRs> getAllRooms() {
        List<Room> rooms = roomRepository.findAllByDeletedIsFalse();
        return rooms.stream()
                .map(room -> conversionService.convert(room, RoomDtoRs.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RoomDtoRs createRoom(RoomDtoRq roomDto) {
        Room room = conversionService.convert(roomDto, Room.class);
        room = roomRepository.save(Objects.requireNonNull(room));
        return conversionService.convert(room, RoomDtoRs.class);
    }

    @Transactional
    @Override
    public RoomDtoRs updateRoom(int id, RoomDtoRq roomDto) {
        Room room = conversionService.convert(roomDto, Room.class);
        Objects.requireNonNull(room).setId(id);
        room = roomRepository.save(room);
        return conversionService.convert(room, RoomDtoRs.class);
    }

    @Override
    public RoomDtoRs getRoom(int id) {
        Room room = roomRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Палаты с таким ID не существует"));
        return conversionService.convert(room, RoomDtoRs.class);
    }

    @Override
    public void deleteRoom(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Палаты с таким ID не существует"));
        room.setDeleted(true);
        roomRepository.save(room);
    }
}
