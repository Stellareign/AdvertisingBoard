package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.AuthService;
import ru.skypro.homework.service.interfaces.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;


    @Override
    public boolean checkPassword(UpdatePasswordDTO updatePasswordDTO) {

//        String newPassword = updatePasswordDTO.getNewPassword();
//        String currentPassword = updatePasswordDTO.getCurrentPassword();
//        String password = userDetails.getPassword();
//        return !newPassword.equals(currentPassword) && newPassword.length() >= 8 && !newPassword.isBlank()
//                && currentPassword.equals(password);
        return true;
    }

    @Override
    public User gerUserByEmail(String email) {
        Example<User> example = Example.of(new User(email));
        return userRepository.findBy(example, query -> query.oneValue());
    }


    @Override
    public void saveRegisterUser(Register register) {

//          User user  =  userMapper.registerToUserConverter()
//                  .convert((MappingContext<Register, User>) register);
        String pass = encoder.encode(register.getPassword());

        User user = new User();
        user.setUsername(register.getUsername());
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setRole(register.getRole());
        user.setPhone(register.getPhone());
        user.setCurrentPassword(pass);
        userRepository.save(user);
    }
}
