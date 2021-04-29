package ru.iteco.fmh.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.AdmissionsStatusE;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConversionServiceFactoryBean factoryBean;

//    Удалить!
//    @Override
//    public List<PatientAdmissionDto> getAllPatients() {
//        List<Patient> patientList = patientRepository.findAll();
//        ConversionService conversionService = factoryBean.getObject();
//        return patientList.stream()
//                .map(i -> conversionService.convert(i, PatientAdmissionDto.class))
//                .collect(Collectors.toList());
//    }

//    TODO: Anastasya
//    @Override
//    public List<PatientDto> getAllPatients() {
//        List<Patient> patientList = patientRepository.findAll();
//        ConversionService conversionService = factoryBean.getObject();
//        return patientList.stream()
//                .map(i -> conversionService.convert(i, PatientDto.class))
//                .collect(Collectors.toList());
//    }

//    TODO: Anastasya
//    @Override
//    public List<PatientDto> getPatientsByStatus(PatientsStatus patientsStatus) {
//        List<Patient> patientList = patientRepository.findByStatus(patientsStatus);
//        ConversionService conversionService = factoryBean.getObject();
//        return patientList.stream()
//                .map(i -> conversionService.convert(i, PatientDto.class))
//                .collect(Collectors.toList());
//    }

//   TODO: ПРОВЕРИТЬ
//         получение списка всех пациентов по статусу - 1 вариант
//         если решаем создавать пациентов с пустой госпитализацией, то
//         пациенты с пустой госпитализацией должны попасть в фильтр "ожидаются"

    @Override
    public List<PatientAdmissionDto> getAllPatientsByStatus(List<String> status) {
        List<Patient> patientList = patientRepository.findAll();
        ConversionService conversionService = factoryBean.getObject();

        // задаем условия для фильтра - фильтровать только по совпадению статусов
        // или отбирать также пациентов с пустой госпитализацией
        // если в метод приходит status Expected
        final Predicate<Patient> patientFilter = status.contains(AdmissionsStatusE.Expected.toString()) ?
                // filterByNullAdmissionAndByStatusComparing
                (patient -> (patient.getCurrentAdmission() == null ||
                            status.contains(patient.getCurrentAdmission().getAdmissionsStatus().toString()))) :

                // filterByStatusComparing
                (patient -> status.contains(patient.getCurrentAdmission().getAdmissionsStatus().toString()));

        return patientList.stream()
                //  отфильтровываем
                .filter(patientFilter)
                //  конвертируем patient в dto
                .map(patient -> conversionService.convert(patient, PatientAdmissionDto.class))
                //  для каждого dto ставим правильные даты и флаги
                .map(this::setAdmissionDates)
                .collect(Collectors.toList());
    }


//    получение списка всех пациентов по статусу -2 вариант
//    если при создании пациента с пустой госпитализацией, создается госпитализация по-умолчанию
//    с пустыми полями, но с заполненным полем статуса - "ожидается"

//    @Override
//    public List<PatientAdmissionDto> getAllPatientsByStatus(List<String> status) {
//        List<Patient> patientList = patientRepository.findAll();
//        ConversionService conversionService = factoryBean.getObject();
//
//        return patientList.stream()
//                .filter(patient -> status.contains(patient.getCurrentAdmission().getAdmissionsStatus().toString()))
//                .map(patient -> conversionService.convert(patient, PatientAdmissionDto.class))
//                .map(this::setAdmissionDates)
//                .collect(Collectors.toList());
//    }


    @Override
    public PatientDto createPatient(PatientDto dto) {
        ConversionService conversionService = factoryBean.getObject();
        Patient entity = conversionService.convert(dto, Patient.class);
        entity = patientRepository.save(entity);
        return conversionService.convert(entity, PatientDto.class);
    }

    @Override
    public PatientDto getPatient(Integer id) {
        Patient patient = patientRepository.getOne(id);
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(patient, PatientDto.class);
    }

    @Override
    public PatientDto updatePatient(PatientDto dto) {

        // TODO: 27.01.2021 подумать над необходимостью метода, так как дублирует  create
        // ВАРИАНТ РЕШЕНИЯ: сделать один метод - createOrUpdatePatient

        ConversionService conversionService = factoryBean.getObject();
        Patient entity = conversionService.convert(dto, Patient.class);
        entity = patientRepository.save(entity);
        return conversionService.convert(entity, PatientDto.class);
    }

    // ставит верные dateIn, dateOut и флаги для отправки на фронт
    private PatientAdmissionDto setAdmissionDates(PatientAdmissionDto patientAdmissionDto) {
        LocalDate factDateIn = patientAdmissionDto.getFactDateIn();
        LocalDate factDateOut = patientAdmissionDto.getFactDateOut();
        LocalDate planDateIn = patientAdmissionDto.getPlanDateIn();
        LocalDate planDateOut = patientAdmissionDto.getPlanDateOut();

        if (factDateIn != null) {
            patientAdmissionDto.setDateIn(factDateIn);
            patientAdmissionDto.setDateInBoolean(true);
        } else {
            patientAdmissionDto.setDateIn(planDateIn);
            patientAdmissionDto.setDateInBoolean(false);
        }

        if (factDateOut != null) {
            patientAdmissionDto.setDateOut(planDateOut);
            patientAdmissionDto.setDateOutBoolean(false);
        }

        return patientAdmissionDto;
    }
}
