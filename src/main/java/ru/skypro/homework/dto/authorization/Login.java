package ru.skypro.homework.dto.authorization;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Login {

    private String username;
    private String password;
}
