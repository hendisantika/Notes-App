package id.my.hendisantika.notesapp.controller;

import id.my.hendisantika.notesapp.model.Note;
import id.my.hendisantika.notesapp.payload.request.NoteRequest;
import id.my.hendisantika.notesapp.security.UserDetailsImpl;
import id.my.hendisantika.notesapp.service.NoteService;
import id.my.hendisantika.notesapp.service.RateLimitingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Operation(
            summary = "List All Notes",
            description = "List All Notes.",
            tags = {"Notes"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Authentication.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    ResponseEntity<?> getNotesByUserId(Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        if (rateLimitingService.allowRequest(userId.toString()))
            return noteService.getNotes(userId);
        return ResponseEntity.status(429).body("Request limit exceeded");
    }

    @GetMapping("/{noteId}")
    @Operation(
            summary = "Get Note By ID",
            description = "Get Note By ID.",
            tags = {"Notes"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Authentication.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    ResponseEntity<?> getNoteByNoteId(Authentication authentication, @PathVariable Long noteId) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        if (rateLimitingService.allowRequest(userId.toString()))
            return noteService.getNoteByNoteId(userId, noteId);
        return ResponseEntity.status(429).body("Request limit exceeded");
    }

    @PostMapping("/")
    @Operation(
            summary = "Add New Note",
            description = "Add New Note.",
            tags = {"Notes"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            NoteRequest.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    ResponseEntity<?> saveNote(@RequestBody NoteRequest noteRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        if (rateLimitingService.allowRequest(userId.toString())) {
            if (userId != null) {
                Note note = new Note();
                note.setUserId(userId);
                note.setTitle(noteRequest.getTitle());
                note.setContent(noteRequest.getContent());
                return noteService.createNote(note);
            }
            return ResponseEntity.badRequest().body("Authentication Failed");
        }
        return ResponseEntity.status(429).body("Request limit exceeded");
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update Note",
            description = "Update Note.",
            tags = {"Notes"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Note.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    ResponseEntity<?> updateNote(@PathVariable Long id,
                                 @RequestBody Note note,
                                 Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        if (rateLimitingService.allowRequest(userId.toString()))
            return noteService.updateNote(id, note, userId);
        return ResponseEntity.status(429).body("Request limit exceeded");
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Note",
            description = "Delete Note.",
            tags = {"Notes"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Note.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    ResponseEntity<?> deleteNoteById(@PathVariable Long id, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        if (rateLimitingService.allowRequest(userId.toString()))
            return noteService.deleteNote(userId, id);
        return ResponseEntity.status(429).body("Request limit exceeded");
    }

    @PostMapping("/{noteId}/share")
    @Operation(
            summary = "Share Note",
            description = "Share Note.",
            tags = {"Notes"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Note.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    ResponseEntity<?> shareNote(@PathVariable Long noteId, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        if (rateLimitingService.allowRequest(userId.toString()))
            return noteService.shareNote(noteId, userId);
        return ResponseEntity.status(429).body("Request limit exceeded");
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search Note",
            description = "Search Note.",
            tags = {"Notes"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Note.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    ResponseEntity<?> searchQuery(@RequestParam String query, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        if (rateLimitingService.allowRequest(userId.toString()))
            return noteService.searchNotes(userId, query);
        return ResponseEntity.status(429).body("Request limit exceeded");
    }
}
