package ru.skypro.homework.dto.authorization;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;
import ru.skypro.homework.dto.Role;

@Data

public class Register {

    private String username; //e-mail при регитсрации
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
