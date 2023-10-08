package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mappers.PasswordMapper;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.MyUserDetailes;
import ru.skypro.homework.service.interfaces.AuthService;
import ru.skypro.homework.service.interfaces.UserService;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    private final PasswordMapper passwordMapper;


    //    private final SecurityUserPrincipal securityUserPrincipal; // проверить
    private final MyUserDetailes myUserDetailes;

    private final User user = new User("pupkin@poy.ru", "Ваня", "Пупкин", Role.USER,
            "+7(123)456-78-90", "qwerty123", LocalDate.from(LocalDate.now()));

    @Override
    public boolean checkPassword(UpdatePasswordDTO updatePasswordDTO) {
        //***********************
//        User userTest = new User("pupkin@poy.ru");
        User user1 = getUserByUsernameFromDB(user.getUsername());

        //********************************
        String newPassword = updatePasswordDTO.getNewPassword();
        String currentPassword = updatePasswordDTO.getCurrentPassword();
        String password = user1.getPassword();

        passwordMapper.passToEntityConverter(updatePasswordDTO);
        return !newPassword.equals(currentPassword) && newPassword.length() >= 8 && !newPassword.isBlank()
                && currentPassword.equals(password);
    }

    @Override
    public User getUserByUsernameFromDB(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDTO convertUserToUserDTO(User user) {
        userMapper.setupMapper();
        return (UserDTO) userMapper.userToDtoConverter();
    }

    @Override
    public User convertUpdateUserDTOtoUser(UpdateUserDTO updateUserDTO) {
        return userMapper.updUserToEntityConverter().convert((MappingContext<UpdateUserDTO, User>) updateUserDTO);
    }

    @Override
    public User updateUser(User user, UpdateUserDTO updateUserDTO) {
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setPhone(updateUserDTO.getPhone());
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveRegisterUser(Register register) {
        userRepository.save(createUserFromRegister(register));
    }

    private User createUserFromRegister(Register register) {
        String pass = encoder.encode(register.getPassword()); // шифрование пароля
        User user = new User();
        user.setUsername(register.getUsername());
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setRole(register.getRole());
        user.setPhone(register.getPhone());
        user.setPassword(pass);
        user.setRegisterDate(LocalDate.from(LocalDate.now()));
        return user;
    }
}

