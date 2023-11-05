package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.AuthUserDTO;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.MapperUtil.UserDTOFactory;

import java.net.MalformedURLException;

@Service
@RequiredArgsConstructor
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
                user.getPhone(), ("/users/image/" + user.getId()), user.getRole());
    }

    @Override
    public User fromUserDTOtoUser(UserDTO userDTO, User user) throws MalformedURLException {

        return new User(user.getId(), userDTO.getEmail(), user.getPassword(), userDTO.getFirstName(),
                userDTO.getLastName(), userDTO.getPhone(), userDTO.getImage(), userDTO.getRole(),
                user.getRegisterDate());
    }

    // **************************** User to UpdateUserDTO // UpdateUserDTO to User ***************************
    @Override
    public UpdateUserDTO fromUserToUpdateUserDTO(User user) {
        return new UpdateUserDTO(user.getFirstName(), user.getLastName(), user.getPhone());
    }

    @Override
    public User fromUpdateUserDTOtoUser(UpdateUserDTO updateUserDTO, User user) {
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setPhone(updateUserDTO.getPhone());
        return user;
    }

    // **************************** User to AuthUserDTO // AuthUserDTO to User ***************************
    @Override
    public AuthUserDTO fromUserToAuthUserDTO(User user) {
        return new AuthUserDTO(user.getUsername(), user.getPassword(), user.getRole());
    }
    @Override
    public User fromAuthUserDTOtoUser(User user, AuthUserDTO authUserDTO){
        return new User(user.getId(), authUserDTO.getUsername(), authUserDTO.getPassword(),user.getFirstName(),
                user.getLastName(), user.getPhone(),  user.getAvatarPath(), authUserDTO.getRole(),
                user.getRegisterDate());
    }
}
