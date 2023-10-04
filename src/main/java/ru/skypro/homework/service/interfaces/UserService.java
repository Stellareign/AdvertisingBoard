package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.entity.User;
@Service
public interface UserService {


    User gerUserByEmail(String email);

    void saveRegisterUser (Register register);

    boolean checkPassword(UpdatePasswordDTO updatePasswordDTO);
}
