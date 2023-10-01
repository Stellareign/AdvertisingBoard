package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.authorization.PasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UpdateUserImageDTO;
import ru.skypro.homework.dto.user.AddUserDTO;
import ru.skypro.homework.entity.Password;
import ru.skypro.homework.service.AuthService;

@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/user")
@Tag(name = "Пользователь")

public class UserController {

    private final AuthService authService;
    AddUserDTO userDTO = new AddUserDTO();

    @Operation(summary = "Обновление пароля")
    @PostMapping("/set_password/")
    public ResponseEntity<PasswordDTO> setPassword(@RequestParam String currentPassword,
                                                   @RequestParam String newPassword,
                                                   Password password) {
        PasswordDTO passwordDTO = new PasswordDTO();
        if (!newPassword.equals(password.getPassword()) && newPassword.length() >= 8 && !newPassword.isBlank()) {
            passwordDTO.setCurrentPassword(password.getPassword());
            passwordDTO.setNewPassword(newPassword);

            password.setPassword(newPassword);

            return ResponseEntity.ok().body(passwordDTO);
        } else if (!currentPassword.equals(password.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me/{email}")
    public ResponseEntity<?> getUser(@RequestParam String eMail) {
        // UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO != null) {
            return ResponseEntity.ok().body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(String firstName, String lastName, String phone) {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName(firstName);
        updateUserDTO.setLastName(lastName);
        updateUserDTO.setPhone(phone);
        return ResponseEntity.ok().body(updateUserDTO);
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping("/me/image")
    public ResponseEntity<?> updateUserImage(String image) {
        UpdateUserImageDTO updateUserImage = new UpdateUserImageDTO();
        AddUserDTO newUserDTO = new AddUserDTO();
        if (image != null) {
            updateUserImage.setImage(image);
            return ResponseEntity.ok().body(newUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
