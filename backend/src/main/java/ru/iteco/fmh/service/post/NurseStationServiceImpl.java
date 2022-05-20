package ru.iteco.fmh.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.converter.post.NurseStationConverter;
import ru.iteco.fmh.dao.repository.NurseStationRepository;
import ru.iteco.fmh.dto.post.NurseStationDto;
import ru.iteco.fmh.dto.post.NurseStationDtoRq;
import ru.iteco.fmh.dto.post.NurseStationDtoRs;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NurseStationServiceImpl implements NurseStationService {

    private final NurseStationRepository nurseStationRepo;
    private final NurseStationConverter nurseStationConverter;

    @Override
    public List<NurseStationDto> getAll() {
        return nurseStationRepo.findAllByDeletedIsFalse().stream()
                .map(nurseStationConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NurseStationDtoRs createOrUpdateNurseStation(int id, NurseStationDtoRq nurseStationDto) {
        var ns = nurseStationConverter.rqToEntity(nurseStationDto);
        ns.setId(id);
        ns = nurseStationRepo.save(ns);
        return nurseStationConverter.toRsDto(ns);
    }

    @Override
    public NurseStationDto getNurseStation(int id) {
        var ns = nurseStationRepo.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Пост с таким ID не существует"));
        return nurseStationConverter.toDto(ns);
    }

    @Override
    public void deleteNurseStation(int id) {
        var ns = nurseStationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пост с таким ID не существует"));
        ns.setDeleted(true);
        nurseStationRepo.save(ns);
    }
}
