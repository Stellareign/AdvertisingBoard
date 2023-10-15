package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.AuthService;
import ru.skypro.homework.service.interfaces.UserService;

@Slf4j
@Service
//@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthServiceImpl(PasswordEncoder encoder, UserRepository userRepository, UserService userService) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public boolean login(String userName, String password) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            log.info("Введены некорректные имя пользователя и/или пароль ");
            return false;
        }
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public boolean register(Register register) {

        if (userRepository.findByUsername(register.getUsername())!=null)
            return false;
        userService.saveRegisterUser(register);
        return true;
    }


}
