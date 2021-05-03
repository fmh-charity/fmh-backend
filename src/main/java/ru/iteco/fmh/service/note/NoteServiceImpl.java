package ru.iteco.fmh.service.note;

import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.Note;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;
    private ConversionServiceFactoryBean factoryBean;

    public NoteServiceImpl(NoteRepository noteRepository, ConversionServiceFactoryBean factoryBean) {
        this.noteRepository = noteRepository;
        this.factoryBean = factoryBean;
    }

    public List<NoteShortInfoDto> getAllNotes() {
        List<Note> list = noteRepository.findAll();
        ConversionService conversionService = factoryBean.getObject();
        return list.stream()
                .map(i -> conversionService.convert(i, NoteShortInfoDto.class))
                .collect(Collectors.toList());
    }

    public NoteDto getNote(Integer id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            ConversionService conversionService = factoryBean.getObject();
            Note note = optionalNote.get();
            return conversionService.convert(note, NoteDto.class);
        } else {
            return null;
        }
    }

    public NoteDto createNote(NoteDto noteDto) {
        ConversionService conversionService = factoryBean.getObject();
        Note note = conversionService.convert(noteDto, Note.class);
        note = noteRepository.save(note);
        return conversionService.convert(note, NoteDto.class);
    }

    @Override
    public List<NoteDto> getPatientNotes(Integer patientId) {
        ConversionService conversionService = factoryBean.getObject();
        return noteRepository.findAllByPatient_IdAndStatusName_active(patientId).stream()
                .map(note -> conversionService.convert(note, NoteDto.class))
                .collect(Collectors.toList());
    }
}
