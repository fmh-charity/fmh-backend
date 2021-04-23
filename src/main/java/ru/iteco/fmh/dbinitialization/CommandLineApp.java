package ru.iteco.fmh.dbinitialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.model.Admission;
import ru.iteco.fmh.model.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

// DB initialization for manual test
@Component
public class CommandLineApp implements CommandLineRunner {
    // DB SIZE
    private static final int CAPACITY_PATIENT = 10;
    private static final int CAPACITY_ADMISSION = 3;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AdmissionRepository admissionRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        var random = new Random();

        // PatientRepository initialization
        var firstNames = List.of("Иван", "Василий", "Петр");
        var middleNames = List.of("Иванов", "Васильев", "Петров");
        var lastNames = List.of("Иванович", "Васильевич", "Петрович");
        var birthdays = List.of(LocalDate.of(1900, 1, 1),
                LocalDate.of(1901, 1, 1),
                LocalDate.of(1902, 1, 1));

        IntStream.range(0, CAPACITY_PATIENT)
                .forEach(i -> {
                    var patient = Patient.builder()
                            .firstName(firstNames.get(random.nextInt(firstNames.size())))
                            .middleName(middleNames.get(random.nextInt(middleNames.size())))
                            .lastName(lastNames.get(random.nextInt(lastNames.size())))
                            .birthday(birthdays.get(random.nextInt(birthdays.size())))
                            .build();
                    patientRepository.save(patient);
                });

        // AdmissionRepository initialization
        var dates = List.of(LocalDate.of(2000, 1, 1),
                LocalDate.of(2001, 1, 1),
                LocalDate.of(2002, 1, 1));
        var facts = List.of(true,false);
        var comments = List.of("admission comment 1", "admission comment 2", "admission comment 3");

        AtomicInteger patientId= new AtomicInteger(0);

        IntStream.range(0, CAPACITY_PATIENT)
                .forEach(i -> {
                    patientId.incrementAndGet();
                    for (int j = 0; j < CAPACITY_ADMISSION; j++) {
                        var admission = Admission.builder()
                                .dateFrom(dates.get(random.nextInt(dates.size())))
                                .dateTo(dates.get(random.nextInt(dates.size())))
                                .factIn(facts.get(random.nextInt(facts.size())))
                                .factOut(facts.get(random.nextInt(facts.size())))
                                .comment(comments.get(random.nextInt(comments.size())))
                                .patient(patientRepository.findById(patientId.get()).get())
                                .build();
                        admissionRepository.save(admission);
                    }
                });
    }
}
