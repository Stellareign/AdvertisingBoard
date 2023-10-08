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
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.interfaces.CommentsService;

import java.util.List;

@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/ads/{adId}/comments")
@Tag(name = "Комментарии")
public class CommentsController {

    private CommentsService commentsService;

    private AdsServiceImpl adsService;

    private MapperUtil mapperUtil;

    public CommentsController(CommentsService commentsService, AdsServiceImpl adsService, MapperUtil mapperUtil) {
        this.commentsService = commentsService;
        this.adsService = adsService;
        this.mapperUtil = mapperUtil;
    }

    @Operation(summary = "Получение списка всех комментариев")
    @GetMapping("")
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
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO comments) {
        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // удаление комментария по id
    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{commentId}")
//    public void deleteComment(@PathVariable int adId , @RequestParam int commentId ) {
    public ResponseEntity<?> deleteComment(@PathVariable int adId , @RequestParam int pk ){
        adsService.getAdById(adId);
        boolean deleteIsOk = commentsService.deleteComment(pk);
        if (deleteIsOk) {
            return new ResponseEntity<>(("Комментарий с id = " + pk + "успешно удалён"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(("Ошибка при попытке удалить комментарий с id = " + pk), HttpStatus.NOT_FOUND);
        }
    }

    // обновление комментария
    @Operation(summary = "Обновление комментария")
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable int adId,@PathVariable int pk, String text) {
        adsService.getAdById(adId);
        commentsService.getComment(pk);
        CreateOrUpdateCommentDTO updateComment = new CreateOrUpdateCommentDTO(text);
        return ResponseEntity.ok(commentsService.updateComment(pk, updateComment));
    }
}
