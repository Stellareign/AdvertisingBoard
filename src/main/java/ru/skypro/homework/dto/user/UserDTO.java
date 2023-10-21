package ru.skypro.homework.dto.user;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

    private int id;
    private String email; // логин
    private String firstName;
    private String lastName;
    private String phone;

    private String avatar; // ссылка на аватар
    private Role role;

}
