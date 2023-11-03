package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
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

    /**
     * Получение списка всех комментариев к объявлению
     *
     * @param adId идентификатор объявления
     * @return список комментариев в формате CommentsDTO
     */
    @Operation(summary = "Получение списка всех комментариев")
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable("id") int adId) {
        CommentsDTO commentsDTO = commentsService.getAllComments(adId);
        return ResponseEntity.ok(commentsDTO);
    }

    /**
     * Метод для добавления нового комментария к объявлению.
     *
     * @param adId             идентификатор объявления
     * @param createCommentDto объект с данными нового комментария
     * @param authentication   данные об аутентификации пользователя
     * @return объект ResponseEntity с добавленным комментарием в формате CommentDTO
     */
    @Operation(summary = "Добавление нового комментария")
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable("id") Integer adId,
                                                 @RequestBody CreateOrUpdateCommentDTO createCommentDto,
                                                 Authentication authentication) {
        CommentDTO commentDTO = commentsService.addComment(createCommentDto, adId, authentication);
        return ResponseEntity.ok(commentDTO);
    }


    /**
     * Метод для удаления комментария.
     *
     * @param adId - идентификатор объявления, к которому относится комментарий
     * @param pk   -  идентификатор комментария, который нужно удалить
     * @return ответ сервера с информацией о результате операции
     */
    @Operation(summary = "Удаление комментария")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @commentRepository.findCommentByPk(#pk).author.username  == authentication.name")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("adId") int adId, @PathVariable("commentId") int pk) {

        commentsService.deleteComment(adId, pk);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Обновление комментария.
     *
     * @param adId             Идентификатор объявления.
     * @param pk               Идентификатор комментария.
     * @param updateCommentDTO DTO с информацией для обновления комментария.
     * @return Обновленный комментарий в формате CommentDTO.
     */
    @Operation(summary = "Обновление комментария")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @commentRepository.findCommentByPk(#pk).author.username == authentication.name")
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable int adId, @PathVariable("commentId") int pk,
                                                    @RequestBody CreateOrUpdateCommentDTO updateCommentDTO) {

        return ResponseEntity.ok(commentsService.updateComment(adId, pk, updateCommentDTO));

    }
}
