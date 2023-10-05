package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.CommentsDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.service.CommentsService;

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
    @GetMapping("/ads/{adId}/comments/all")
    public ResponseEntity<CommentsDTO> getComment(Ad adsId) {
        List<Comments> allComments = commentsService.result(adsId);
        CommentsDTO commentsDTO = new CommentsDTO(allComments.size(), allComments);
        if (!allComments.isEmpty()) {
            return ResponseEntity.ok().body(commentsDTO);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // добавление комментариев
    @Operation(summary = "Добавление нового комментария")
    @PostMapping
    public ResponseEntity<Comments> addComment(@RequestBody Comments comments) {
        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // удаление комментария по id
    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/ads/{adId}/comments/comment/{commentId}")
    public void deleteComment(@RequestParam int adId , @RequestParam int commentId ) {
    }

    // обновление комментария
    @Operation(summary = "Обновление комментария")
    @PutMapping("/ads/{adId}/comments/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable int adId,@PathVariable int commentId, String text) {
        CreateOrUpdateCommentDTO createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        return ResponseEntity.ok(createOrUpdateCommentDTO);
    }
}
