package ru.iteco.fmh.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.NurseStationRepository;
import ru.iteco.fmh.dto.nurse_station.NurseStationDto;
import ru.iteco.fmh.model.NurseStation;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NurseStationServiceImpl implements NurseStationService {

    private final NurseStationRepository nurseStationRepo;
    private final ConversionService conversionService;

    @Override
    public List<NurseStationDto> getAll() {
        return nurseStationRepo.findAll().stream()
                .map(ns -> conversionService.convert(ns, NurseStationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public NurseStationDto createOrUpdateNurseStation(NurseStationDto nurseStationDto) {
        var ns = conversionService.convert(nurseStationDto, NurseStation.class);
        ns = nurseStationRepo.save(ns);
        return conversionService.convert(ns, NurseStationDto.class);
    }

    @Override
    public NurseStationDto getNurseStation(int id) {
        var ns = nurseStationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пост с таким ID не существует"));
        return conversionService.convert(ns, NurseStationDto.class);
    }

    @Override
    public void deleteNurseStation(int id) {
        var ns = nurseStationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пост с таким ID не существует"));
        ns.setDeleted(true);
        nurseStationRepo.save(ns);
    }
}
