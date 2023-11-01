package ru.skypro.homework.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthUserDTO {
    /**
     * класс для авторизации по ролям
     */
    private String username;
    private String password;
    private Role role;
}
