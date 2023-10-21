package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.AuthUserDTO;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;

import java.net.MalformedURLException;

@Service
public interface UserDTOFactory {
    //******************************** пароли  *********************
    UpdateUserDTO fromUserToUpdatePassworDTO(User user);

    User fromUpdatePasswordDTOtoUser(User user, UpdatePasswordDTO updatePasswordDTO);

    // **************************** User to UserDTO // UserDTO to User ***************************
    UserDTO fromUserToUserDTO(User user);

    User fromUserDTOtoUser(UserDTO userDTO, User user) throws MalformedURLException;

    // **************************** User to UpdateUserDTO // UpdateUserDTO to User ***************************
    UpdateUserDTO fromUserToUpdateUserDTO(User user);

    User fromUpdateUserDTOtoUser(UpdateUserDTO updateUserDTO, User user);

    // **************************** User to AuthUserDTO // AuthUserDTO to User ***************************
    AuthUserDTO fromUserToAuthUserDTO(User user);

    User fromAuthUserDTOtoUser(User user, AuthUserDTO authUserDTO);
}
