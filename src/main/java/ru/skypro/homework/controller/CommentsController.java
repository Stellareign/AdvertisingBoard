package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO>  getComments(@PathVariable("id") int adId) {
            CommentsDTO commentsDTO = commentsService.getAllComments(adId);
            return ResponseEntity.ok(commentsDTO);
    }

    // добавление комментариев
    @Operation(summary = "Добавление нового комментария")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable("id") Integer adId,
                                                 @RequestBody CreateOrUpdateCommentDTO createCommentDto,
                                                 Authentication authentication) {
        CommentDTO commentDTO = commentsService.addComment(createCommentDto, adId, authentication);
        return ResponseEntity.ok(commentDTO);
    }

    // удаление комментария по id
    @Operation(summary = "Удаление комментария")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @commentsService.getAuthorByCommentId(#pk).username == authentication.principal.username")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(@PathVariable("adId") int adId , @PathVariable("commentId") int pk ) {

//    public void deleteComment(@PathVariable("commentId") int pk ) {
      commentsService.deleteComment(adId, pk);
    }

    // обновление комментария
        @Operation(summary = "Обновление комментария")
        @PreAuthorize("hasAuthority('ROLE_ADMIN') or @commentsService.getAuthorByCommentId(#pk).username == authentication.principal.username")
        @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable int adId,@PathVariable("commentId") int pk,
                                                    @RequestBody CreateOrUpdateCommentDTO updateCommentDTO) {
              return ResponseEntity.ok(commentsService.updateComment(adId, pk, updateCommentDTO));
    }
}
