package ru.iteco.fmh.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.converter.room.RoomConverter;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.room.RoomDto;
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
    private final RoomConverter roomConverter;

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAllByDeletedIsFalse();
        return rooms.stream()
                .map(roomConverter::roomEntityToRoomDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RoomDtoRs createOrUpdateRoom(int id, RoomDtoRq roomDto) {
        roomDto.setId(id);
        Room room = roomConverter.roomDtoRqToRoomEntity(roomDto);
        room = roomRepository.save(Objects.requireNonNull(room));
        return roomConverter.roomEntityToRoomDtoRs(room);
    }

    @Override
    public RoomDto getRoom(int id) {
        Room room = roomRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Палаты с таким ID не существует"));
        return roomConverter.roomEntityToRoomDto(room);
    }

    @Override
    public void deleteRoom(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Палаты с таким ID не существует"));
        room.setDeleted(true);
        roomRepository.save(room);
    }
}
