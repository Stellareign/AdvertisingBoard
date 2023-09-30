package ru.skypro.homework.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.dto.Role;

@Data

public class AddUserDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private Role role;
    private String image;


}
