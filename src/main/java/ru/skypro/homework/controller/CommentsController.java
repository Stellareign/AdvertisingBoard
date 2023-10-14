package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.config.MapperUtil;
import ru.skypro.homework.dto.comments.CommentsDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.interfaces.CommentsService;

import java.util.Map;
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

    private AdsServiceImpl adsService;

    private MapperUtil mapperUtil;

    public CommentsController(CommentsService commentsService, AdsServiceImpl adsService, MapperUtil mapperUtil) {
        this.commentsService = commentsService;
        this.adsService = adsService;
        this.mapperUtil = mapperUtil;
    }

    @Operation(summary = "Получение списка всех комментариев")
    @GetMapping("/{id}/comments\n")
    public ResponseEntity<CommentsDTO> getComment(@PathVariable int adId) {
        Optional<Ad> ad = adsService.getAdById(adId);
        Map<Integer, Comment> allComments = commentsService.getAllComments();
        CommentsDTO commentsDTO = new CommentsDTO(allComments.size(), allComments);
        if (!allComments.isEmpty() & ad.isPresent()) {
            return ResponseEntity.ok().body(commentsDTO);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // добавление комментариев
    @Operation(summary = "Добавление нового комментария")
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable Comment pk, @RequestBody String text) {
        if (pk != null) {
            return ResponseEntity.ok(pk);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // удаление комментария по id
    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{adId}/comments/{commentId}")
//    public void deleteComment(@PathVariable int adId , @RequestParam int commentId ) {
    public ResponseEntity<?> deleteComment(@PathVariable int adId , @PathVariable int pk ){
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
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable int adId,@PathVariable int pk, @RequestBody String text) {
        adsService.getAdById(adId);
        commentsService.getComment(pk);
        CreateOrUpdateCommentDTO updateComment = new CreateOrUpdateCommentDTO(text);
        return ResponseEntity.ok(commentsService.updateComment(pk, updateComment));
    }
}
