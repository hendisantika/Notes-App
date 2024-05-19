package id.my.hendisantika.notesapp.service;

import id.my.hendisantika.notesapp.model.Note;
import id.my.hendisantika.notesapp.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : Notes-App
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/20/24
 * Time: 06:41
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public ResponseEntity<?> getNotes(Long userId) {
        Optional<List<Note>> notes = noteRepository.findByUserId(userId);
        if (notes.isEmpty())
            return ResponseEntity.badRequest().body("Error: No notes found");
        return ResponseEntity.ok(notes);
    }

    public ResponseEntity<?> getNoteByNoteId(Long userId, Long noteId) {
        Optional<Note> notes = noteRepository.findByNoteId(noteId);
        if (notes.isEmpty())
            return ResponseEntity.badRequest().body("Error: No note found.");
        else if (!Objects.equals(notes.get().getUserId(), userId) && !notes.get().getIsNoteShared())
            return ResponseEntity.badRequest().body("Error: Note access denied.");
        return ResponseEntity.ok(notes);
    }

    public ResponseEntity<?> createNote(Note note) {
        note.setIsNoteShared(false);
        noteRepository.save(note);
        return ResponseEntity.ok("Note saved successfully.");
    }
}
