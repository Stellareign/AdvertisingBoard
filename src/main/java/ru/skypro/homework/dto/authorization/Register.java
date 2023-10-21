package ru.skypro.homework.dto.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Register {

    private String username; //e-mail при регистрации
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

}
