package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.CommentsDTO;
import ru.skypro.homework.dto.comments.NewCommentsDTO;
import ru.skypro.homework.dto.comments.UpdateCommentsDTO;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.service.interfaces.CommentsService;

import java.util.List;

@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/comments")
@Tag(name = "Комментарии")
public class CommentsController {

    private CommentsService commentsService;

    @Operation(summary = "Получение списка всех комментариев")
    @GetMapping
    public ResponseEntity<CommentsDTO> getComment(int adsId) {
        List<Comments> allComments = commentsService.allComm(adsId);
        CommentsDTO commentsDTO = new CommentsDTO(allComments.size(), allComments);
        if (!allComments.isEmpty()) {
            return ResponseEntity.ok().body(commentsDTO);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // добавление комментариев
    @Operation(summary = "Добавление нового комментария")
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody NewCommentsDTO comments) {
        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // удаление комментария по id
    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public void deleteComment(@RequestParam int id) {
    }

    // обновление комментария
    @Operation(summary = "Обновление комментария")
    @PatchMapping
    public ResponseEntity<?> updateComment(@RequestParam int adId, int commentId, String text) {
        UpdateCommentsDTO updateCommentsDTO = new UpdateCommentsDTO();
        return ResponseEntity.ok(updateCommentsDTO);
    }
}
