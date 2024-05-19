package id.my.hendisantika.notesapp.service;

import id.my.hendisantika.notesapp.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
