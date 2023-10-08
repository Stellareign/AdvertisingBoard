package ru.skypro.homework.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;

@Data
@Getter
@Setter
@Component
public class UserDTO {

    private int id;
    private String username; // логин
    private String firstName;
    private String lastName;
    private String phone;

    private String image;
    private Role role;

}
