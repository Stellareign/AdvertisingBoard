package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.authorization.Login;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.service.interfaces.AuthService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Вы вошли в свою учётную запись",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Login.class))}
            ),
            @ApiResponse(
                    responseCode = "401", description = "Неверный логин или пароль"
            ),
    })
    public ResponseEntity<?> login(@RequestBody Login login) {
        if (authService.login(login.getUsername(), login.getPassword())) {
            log.info("Вы вошли в свою учётную запись");
            return ResponseEntity.ok().build();
        } else {
            log.info("Вход в учётную запись не выполнен!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Пользователь зарегистрирован",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Register.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "Неверный запрос, пользователь с таким именем уже есть в базе данных"
            ),
    })
    public ResponseEntity<Register> register(@RequestBody Register register){

        if (authService.register(register)) {
            log.info("Вы зарегистрированы!");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        log.info("Ошибка регистрации!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
