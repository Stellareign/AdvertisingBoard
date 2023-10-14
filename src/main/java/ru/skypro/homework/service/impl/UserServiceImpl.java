package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.UserDTOFactory;
import ru.skypro.homework.service.interfaces.UserService;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserDTOFactory userDTOFactory;


    //    private final MyUserDetails securityUserPrincipal; // проверить

    @Override
    public boolean checkPassword(UpdatePasswordDTO updatePasswordDTO, String username) throws UsernameNotFoundException {

        String newPassword = updatePasswordDTO.getNewPassword();
        String currentPassword = updatePasswordDTO.getCurrentPassword();

        User user = userRepository.findByUsername(username);
        String password = encoder.encode(user.getPassword()); // я бы добавила ввод текущего пароля при смене пароля

        if (!newPassword.equals(currentPassword) && newPassword.length() >= 8 && !newPassword.isBlank() &&
        !newPassword.equals(user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);

            return true;
        }
        log.info("Пароль не соответствует требованиям.");
        return false;

    }


   private User getUserByUsernameFromDB(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDTO getUserForGetController(String username) {
        User user = getUserByUsernameFromDB(username);
        return userDTOFactory.fromUserToUserDTO(user);
    }


    @Override
    public UserDTO updateUser(String username, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findByUsername(username);
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setPhone(updateUserDTO.getPhone());
        userRepository.save(user);
        return userDTOFactory.fromUserToUserDTO(user);
    }

    @Override
    public boolean checkUser(String username) {
        return getUserByUsernameFromDB(username) != null;
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

