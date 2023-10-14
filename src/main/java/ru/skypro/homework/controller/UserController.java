package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.interfaces.UserService;

import java.io.IOException;


@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")

public class UserController {

    private final ImageRepository imageRepository;
    private final UserService userService;


    // *************************** ОБНОВЛЕНИЕ ПАРОЛЯ ********************
    @Operation(summary = "Обновление пароля пользователя")
    @PostMapping("/set_password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Пароль обновлён",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdatePasswordDTO.class))}),
            @ApiResponse(
                    responseCode = "401", description = "Ошибка при авторизации"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Недостаточно прав для выполнения операции"
            ),
    })
    public ResponseEntity<UpdatePasswordDTO> setPassword(@RequestBody UpdatePasswordDTO updatePasswordDTO,
                                                         Authentication authentication) {
        log.info("Изменить пароль: " + updatePasswordDTO);
        boolean checkPassword = userService.checkPassword(updatePasswordDTO, authentication.getName());
        if (checkPassword) {
            return ResponseEntity.ok().body(updatePasswordDTO);

        } else if (!checkPassword) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    //****************************** ПОЛУЧЕНИЕ ИНФО О ПОЛЬЗОВАТЕЛЕ ************************************
    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Данные пользователя получены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(
                    responseCode = "401", description = "Ошибка при авторизации"
            )
    })
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {

        if (userService.checkUser(authentication.getName())) {

            return ResponseEntity.ok().body(userService.getUserForGetController(authentication.getName()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //****************************** ОБНОВЛЕНИЕ ИНФО О ПОЛЬЗОВАТЕЛЕ **************************
    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @PatchMapping("/me")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Данные пользователя обновлены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(
                    responseCode = "401", description = "Ошибка при авторизации"
            )
    })
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO, Authentication authentication) {

        if (userService.checkUser(authentication.getName())) {
            return ResponseEntity.ok().body(userService.updateUser(authentication.getName(), updateUserDTO));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //****************************** ОБНОВЛЕНИЕ АВАТАРА ПОЛЬЗОВАТЕЛЯ **************************
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Аватар пользователя обновлён",
                    content = {@Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = Avatar.class))}),
            @ApiResponse(
                    responseCode = "401", description = "Ошибка при авторизации"
            )
    })
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image) throws IOException {

        UserDTO newUserDTO = new UserDTO();
        if (!image.isEmpty() && image.getContentType().startsWith("image/")) {
            byte[] imageData = image.getBytes();

            // для проверки. Логику доработать и перенести в сервис
            Avatar newAvatar = new Avatar();
            newAvatar.setImageData(imageData);
            imageRepository.save(newAvatar);

            return ResponseEntity.ok().body(newUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
