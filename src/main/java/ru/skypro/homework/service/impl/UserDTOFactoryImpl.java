package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.interfaces.UserDTOFactory;

@Service
public class UserDTOFactoryImpl implements UserDTOFactory {

    //******************************** пароли  *********************
    @Override
    public UpdateUserDTO fromUserToUpdatePassworDTO(User user) {
        return new UpdateUserDTO(user.getFirstName(), user.getLastName(), user.getPhone());
    }

    @Override
    public User fromUpdatePasswordDTOtoUser(User user, UpdatePasswordDTO updatePasswordDTO) {
        return new User(user.getUsername(), user.getFirstName(), user.getLastName(), user.getRole(), user.getPhone(),
                updatePasswordDTO.getNewPassword(), user.getRegisterDate());
    }

    // **************************** User to UserDTO // UserDTO to User ***************************
    @Override
    public UserDTO fromUserToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getPhone(), user.getImage(), user.getRole());
    }

    @Override
    public User fromUserDTOtoUser(UserDTO userDTO, User user) {
        return new User(userDTO.getUsername(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getRole(),
                userDTO.getPhone(), userDTO.getImage(), user.getRegisterDate());
    }

    // **************************** User to UpdateUserDTO // UpdateUserDTO to User ***************************
    @Override
    public UpdateUserDTO fromUserToUpdateUserDTO(User user) {
        return new UpdateUserDTO(user.getFirstName(), user.getLastName(), user.getPhone());
    }

    @Override
    public User fromUpdateUserDTOtoUser(UpdateUserDTO updateUserDTO, User user) {
        return new User(user.getUsername(), updateUserDTO.getFirstName(), updateUserDTO.getLastName(), user.getRole(),
                updateUserDTO.getPhone(), user.getImage(), user.getRegisterDate());
    }
}
