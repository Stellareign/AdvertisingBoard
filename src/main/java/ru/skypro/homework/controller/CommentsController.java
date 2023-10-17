package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.config.MapperUtil;
import ru.skypro.homework.dto.comments.CommentDTO;
import ru.skypro.homework.dto.comments.CommentsDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.interfaces.AdsService;
import ru.skypro.homework.service.interfaces.CommentsService;

import java.util.List;
import java.util.Optional;

@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии")
public class CommentsController {

    private CommentsService commentsService;

    private AdsService adsService;


    public CommentsController(CommentsService commentsService, AdsService adsService) {
        this.commentsService = commentsService;
        this.adsService = adsService;
    }

    @Operation(summary = "Получение списка всех комментариев")
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO>  getComments(@PathVariable int adId) {
        try {
            CommentsDTO commentsDTO = commentsService.getAllComments(adId);
            return ResponseEntity.ok(commentsDTO);
        } catch (RuntimeException e) {
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    // добавление комментариев
    @Operation(summary = "Добавление нового комментария")
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Integer pk, @RequestBody CreateOrUpdateCommentDTO COUComment, String userInfo ) {
        try {
            CommentDTO commentDTO = commentsService.addComment(COUComment, pk, userInfo);
            return ResponseEntity.ok(commentDTO);
        } catch (RuntimeException e) {
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // удаление комментария по id
    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{adId}/comments/{commentId}")
//    public void deleteComment(@PathVariable int adId , @RequestParam int commentId ) {
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable int adId , @PathVariable int pk ) {
        try {
            commentsService.deleteComment(adId, pk);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // обновление комментария
        @Operation(summary = "Обновление комментария")
        @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable int adId,@PathVariable int pk, @RequestBody CreateOrUpdateCommentDTO COUComment) {
          try {


              commentsService.updateComment(adId, pk, COUComment);
              return ResponseEntity.ok().build();
          } catch (RuntimeException e) {
              e.getStackTrace();
              return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
          }

    }
}
