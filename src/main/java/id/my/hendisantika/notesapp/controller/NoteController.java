package id.my.hendisantika.notesapp.controller;

import id.my.hendisantika.notesapp.service.NoteService;
import id.my.hendisantika.notesapp.service.RateLimitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : Notes-App
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/20/24
 * Time: 06:57
 * To change this template use File | Settings | File Templates.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notes")
@Transactional
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    private final RateLimitingService rateLimitingService;
}
