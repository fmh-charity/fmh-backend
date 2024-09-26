package ru.iteco.fmh.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.converter.post.NurseStationConverter;
import ru.iteco.fmh.dao.repository.NurseStationRepository;
import ru.iteco.fmh.dto.post.NurseStationDto;
import ru.iteco.fmh.dto.post.NurseStationDtoRq;
import ru.iteco.fmh.dto.post.NurseStationDtoRs;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.NurseStation;

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
    public NurseStationDtoRs updateNurseStation(int id, NurseStationDtoRq nurseStationDto) {
        NurseStation nurseStation = nurseStationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найденост с id = " + id));
        updateEntity(nurseStation, nurseStationDto);
        NurseStation updatedNurseStation = nurseStationRepo.save(nurseStation);
        return nurseStationConverter.toRsDto(updatedNurseStation);
    }

    private void updateEntity(NurseStation nurseStation, NurseStationDtoRq nurseStationDto) {
        nurseStation.setName(nurseStationDto.getName());
        nurseStation.setComment(nurseStationDto.getComment());
    }


    @Override
    public NurseStationDtoRs createNurseStation(NurseStationDtoRq nurseStationDto) {
        var ns = nurseStationConverter.rqToEntity(nurseStationDto);
        ns = nurseStationRepo.save(ns);
        return nurseStationConverter.toRsDto(ns);
    }

    @Override
    public NurseStationDto getNurseStation(int id) {
        var ns = nurseStationRepo.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException("Пост с таким ID не существует"));
        return nurseStationConverter.toDto(ns);
    }

    @Override
    public void deleteNurseStation(int id) {
        var ns = nurseStationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Пост с таким ID не существует"));
        ns.setDeleted(true);
        nurseStationRepo.save(ns);
    }
}
