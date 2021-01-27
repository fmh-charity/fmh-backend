package ru.iteco.fmh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dto.NoteDto;
import ru.iteco.fmh.dto.NoteShortInfoDto;
import ru.iteco.fmh.model.Note;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService implements INoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ConversionServiceFactoryBean factoryBean;


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

}
