package id.my.hendisantika.notesapp.repository;

import id.my.hendisantika.notesapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : Notes-App
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/20/24
 * Time: 06:38
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<List<Note>> findByUserIdAndTitleContainingOrContentContaining(Long userId, String title, String content);

    Optional<List<Note>> findByUserId(Long userId);

    Optional<Note> findByNoteId(Long noteId);

    Optional<Note> findByUserIdAndNoteId(Long userId, Long noteId);

    Note save(Note note);

    void deleteByUserIdAndNoteId(Long userId, Long noteId);
}
