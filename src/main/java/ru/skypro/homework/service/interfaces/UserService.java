package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.entity.User;

@Service
public interface UserService {


    boolean checkPassword(UpdatePasswordDTO updatePasswordDTO);

    User getUserByUsernameFromDB(String username);

    User updateUser(User user, UpdateUserDTO updateUserDTO);

    void saveRegisterUser(Register register);
}
