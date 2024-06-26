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

    public ResponseEntity<?> deleteNote(Long userId, Long noteId) {
        noteRepository.deleteByUserIdAndNoteId(userId, noteId);
        return ResponseEntity.ok("Note deleted successfully.");
    }

    public ResponseEntity<?> updateNote(Long id, Note newNote, Long userId) {
        Optional<Note> oldNote = noteRepository.findByUserIdAndNoteId(userId, id);
        if (oldNote.isPresent()) {
            oldNote.get().setTitle(newNote.getTitle());
            oldNote.get().setContent(newNote.getContent());
            noteRepository.save(oldNote.get());
            return ResponseEntity.ok("Note updated successfully.");
        }
        return ResponseEntity.badRequest().body("Note with given id does not exist.");
    }

    public ResponseEntity<?> searchNotes(Long userId, String query) {
        Optional<List<Note>> notes = noteRepository.findByUserIdAndTitleContainingOrContentContaining(userId, query, query);
        if (notes.isPresent())
            return ResponseEntity.ok(notes);
        return ResponseEntity.badRequest().body("No results found.");
    }

    public ResponseEntity<?> shareNote(Long noteId, Long userId) {
        // Check if the current user owns the note
        Optional<Note> note = noteRepository.findByUserIdAndNoteId(userId, noteId);
        if (note.isPresent()) {
            // Share the note
            note.get().setIsNoteShared(true);
            noteRepository.save(note.get());
            return ResponseEntity.ok("Shared the note successfully.");
        }
        return ResponseEntity.badRequest().body("Given note is not owned by the user.");
    }
}
