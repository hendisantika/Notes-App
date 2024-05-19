package id.my.hendisantika.notesapp.controller;

import id.my.hendisantika.notesapp.security.UserDetailsImpl;
import id.my.hendisantika.notesapp.service.NoteService;
import id.my.hendisantika.notesapp.service.RateLimitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/")
    ResponseEntity<?> getNotesByUserId(Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        if (rateLimitingService.allowRequest(userId.toString()))
            return noteService.getNotes(userId);
        return ResponseEntity.status(429).body("Request limit exceeded");
    }

    @GetMapping("/{noteId}")
    ResponseEntity<?> getNoteByNoteId(Authentication authentication, @PathVariable Long noteId) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        if (rateLimitingService.allowRequest(userId.toString()))
            return noteService.getNoteByNoteId(userId, noteId);
        return ResponseEntity.status(429).body("Request limit exceeded");
    }
}
