package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;

@Service
public interface UserService {


    boolean checkPassword(UpdatePasswordDTO updatePasswordDTO, String username) throws UsernameNotFoundException;

    User getUserByUsernameFromDB(String username);

    UserDTO updateUser(User user, UpdateUserDTO updateUserDTO);

    boolean checkUser(String username);

    void saveRegisterUser(Register register);
}
