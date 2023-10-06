package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mappers.PasswordMapper;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.AuthService;
import ru.skypro.homework.service.interfaces.UserService;

import java.io.IOException;

import static ru.skypro.homework.service.impl.AuthServiceImpl.AUTHORISE;

@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")

public class UserController {


    private final UserMapper userMapper;

    private final PasswordMapper passwordMapper;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final AuthService authService;


    @Operation(summary = "Обновление пароля пользователя")
    @PostMapping("/set_password")
    @ApiResponses(value = {                                                     // нужно понимание!
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
    public ResponseEntity<UpdatePasswordDTO> setPassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        boolean checkPassword = userService.checkPassword(updatePasswordDTO);
        if (checkPassword) {
            passwordMapper.passToEntityConverter(updatePasswordDTO);
            return ResponseEntity.ok().body(updatePasswordDTO);

        } else if (!checkPassword) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me")
    @ApiResponses(value = {                                                     // нужно понимание!
            @ApiResponse(responseCode = "200",
                    description = "Данные пользователя получены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(
                    responseCode = "401", description = "Ошибка при авторизации"
            )
    })
    public ResponseEntity<UserDTO> getUser(@RequestParam String email) {
        User user = userService.gerUserByEmail(email);
        if (user != null) {
            UserDTO userDTO = userMapper.userToDtoConverter().convert((MappingContext<User, UserDTO>) user);
            return ResponseEntity.ok().body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @PatchMapping("/me")
    @ApiResponses(value = {                                                     // нужно понимание!
            @ApiResponse(responseCode = "200",
                    description = "Данные пользователя обновлены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateUserDTO.class))}),
            @ApiResponse(
                    responseCode = "401", description = "Ошибка при авторизации"
            )
    })
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        if (AUTHORISE) {
            return ResponseEntity.ok().body(updateUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {                                                     // нужно понимание!
            @ApiResponse(responseCode = "200",
                    description = "Аватар пользователя обновлён",
                    content = {@Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = Image.class))}),
            @ApiResponse(
                    responseCode = "401", description = "Ошибка при авторизации"
            )
    })
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image) throws IOException {

        UserDTO newUserDTO = new UserDTO();
        if (AUTHORISE && !image.isEmpty() && image.getContentType().startsWith("image/")) {
            byte[]  imageData = image.getBytes();

            // для проверки. Логику доработать и перенести в сервис
            Image newAvatar = new Image();
            newAvatar.setImageData(imageData);
            newAvatar.setImageSize(image.getSize());
            imageRepository.save(newAvatar);

            return ResponseEntity.ok().body(newUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
