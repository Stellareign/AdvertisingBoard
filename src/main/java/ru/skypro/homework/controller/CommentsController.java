package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.CommentDTO;
import ru.skypro.homework.dto.comments.CommentsDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.service.interfaces.CommentsService;

@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии")
public class CommentsController {

    private final CommentsService commentsService;

    @Operation(summary = "Получение списка всех комментариев")
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO>  getComments(@PathVariable("id") int adId) {
            CommentsDTO commentsDTO = commentsService.getAllComments(adId);
            return ResponseEntity.ok(commentsDTO);
    }

    // добавление комментариев
    @Operation(summary = "Добавление нового комментария")
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable("id") Integer adId,
                                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateComment,
                                                 Authentication authentication) {
            CommentDTO commentDTO = commentsService.addComment(createOrUpdateComment, adId, authentication);
            return ResponseEntity.ok(commentDTO);
    }

    // удаление комментария по id
    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(@PathVariable("adId") int adId , @PathVariable("commentId") int pk ) {

//    public void deleteComment(@PathVariable("commentId") int pk ) {
      commentsService.deleteComment(adId, pk);
    }

    // обновление комментария
        @Operation(summary = "Обновление комментария")
        @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable int adId,@PathVariable("commentId") int pk,
                                                    @RequestBody CreateOrUpdateCommentDTO COUComment) {
              return ResponseEntity.ok(commentsService.updateComment(adId, pk, COUComment));
    }
}
