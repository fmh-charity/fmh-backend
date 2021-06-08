package ru.iteco.fmh.service.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.Claim;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.StatusE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.StatusE.ACTIVE;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final ConversionServiceFactoryBean factoryBean;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, ConversionServiceFactoryBean factoryBean) {
        this.noteRepository = noteRepository;
        this.factoryBean = factoryBean;
    }

    @Override
    public List<NoteShortInfoDto> getAllNotes() {
        List<Note> list = noteRepository.findAllByStatusOrderByPlanExecuteDate(ACTIVE);
        ConversionService conversionService = factoryBean.getObject();
        return list.stream()
                .map(i -> conversionService.convert(i, NoteShortInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer createNote(NoteDto noteDto) {
        Note note = factoryBean.getObject().convert(noteDto, Note.class);
        return noteRepository.save(note).getId();
    }


    @Transactional
    @Override
    public NoteDto updateNote(NoteDto noteDto) {
        ConversionService conversionService = factoryBean.getObject();
        Note note = conversionService.convert(noteDto, Note.class);
        if (ACTIVE.equals(note.getStatus())){
            note = noteRepository.save(note);
            return  conversionService.convert(note, NoteDto.class);
        }else {
            throw new IllegalArgumentException("невозможно изменить записку с данным статусом");
        }
    }


    @Override
    public NoteDto getNote(Integer id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            ConversionService conversionService = factoryBean.getObject();
            Note note = optionalNote.get();
            return conversionService.convert(note, NoteDto.class);
        } else {
            throw new IllegalArgumentException("записка не найдена!");
        }
    }


    @Override
    public List<NoteDto> getPatientNotes(Integer patientId) {
        ConversionService conversionService = factoryBean.getObject();
        return noteRepository.findAllByPatient_IdAndDeletedIsFalseAndStatus(patientId, ACTIVE).stream()
                .map(note -> conversionService.convert(note, NoteDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public NoteDto addComment(Integer noteId, String comment) {
        Optional<Note> optionalNote = noteRepository.findById(noteId);

        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            if (!note.getComment().isEmpty()) {
                note.setComment(note.getComment().concat(", ").concat(comment));
            } else {
                note.setComment(comment);
            }
            note = noteRepository.save(note);
            ConversionService conversionService = factoryBean.getObject();
            return conversionService.convert(note, NoteDto.class);
        } else {
            throw new IllegalArgumentException("записка не найдена!");
        }
    }

    @Transactional
    @Override
    public NoteDto changeStatus(Integer noteId, StatusE status) {
        Optional<Note> optionalNote = noteRepository.findById(noteId);

        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            ConversionService conversionService = factoryBean.getObject();
            if (ACTIVE.equals(note.getStatus())) {
                status.changeStatus(note);
                note = noteRepository.save(note);
                return conversionService.convert(note, NoteDto.class);
            } else {
                throw new IllegalArgumentException("невозможно изменить статус неактивной записки!");
            }

        } else {
           throw new IllegalArgumentException("записка не найдена!");
        }

    }
}
