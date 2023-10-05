package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mappers.PasswordMapper;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.AuthService;
import ru.skypro.homework.service.interfaces.ImageService;
import ru.skypro.homework.service.interfaces.UserService;

@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")

public class UserController {

    private final AuthService authService;
    private final UserMapper userMapper;


    private final PasswordMapper passwordMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserService userService;



    @Operation(summary = "Обновление пароля пользователя")
    @PostMapping("/set_password")
    public ResponseEntity<UpdatePasswordDTO> setPassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        boolean checkPassword = userService.checkPassword(updatePasswordDTO);
       if(checkPassword){
            passwordMapper.passToEntityConverter(updatePasswordDTO);
            return ResponseEntity.ok().body(updatePasswordDTO);

        } else if (!checkPassword) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(@RequestParam String email) {
        User user =userService.gerUserByEmail(email);
        if (user != null) {
            UserDTO userDTO = userMapper.userToDtoConverter().convert((MappingContext<User, UserDTO>) user);
            return ResponseEntity.ok().body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {

        return ResponseEntity.ok().body(updateUserDTO);
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image) {

        UserDTO newUserDTO = new UserDTO();
        if (!image.isEmpty() && image.getContentType().startsWith("image/")) {

            return ResponseEntity.ok().body(newUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
