package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;

import java.io.IOException;

@Service
public interface UserService {


    boolean checkUpdatePassword(UpdatePasswordDTO updatePasswordDTO, String username) throws UsernameNotFoundException;

    UserDTO getUserForGetController(String username);

    UserDTO updateUser(String username, UpdateUserDTO updateUserDTO);

    boolean checkUser(String username);

    void saveRegisterUser(Register register) ;

    //****************************************** ОБНОВЛЕНИЕ АВАТАРА ЮЗЕРА  *********************************************
    UserDTO updateUserAvatar (Authentication authentication, MultipartFile image) throws IOException;
}
