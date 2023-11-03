package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;

    private final UserService userService;

    private final UserRepository userRepository;


    /**
     * Метод входа пользователя в личный кабинет
     *
     * @param userName - логин (email) пользователя
     * @param password - пароль
     * @return
     * @see UserRepository#findByUsername(String)
     * расшифровка и проверка пароля
     * @see PasswordEncoder#encode(CharSequence)
     * @see PasswordEncoder#matches(CharSequence, String)
     */
    @Override
    public boolean login(String userName, String password) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            log.info("Неправильные имя пользователя или пароль!");
            return false;
        }
        return encoder.matches(password, user.getPassword());
    }

    /**
     * Метод регистрации пользователя в сервисе
     *
     * @param register
     * @return true если пользователь ранее не был зарегистрирован с указанным username
     * @see UserRepository#findByUsername(String)
     * @see UserRepository#save(Object)
     */
    @Override
    public boolean register(Register register) {
        if (userRepository.findByUsername(register.getUsername()) != null) {
            return false;
        }
        userService.saveRegisterUser(register);
        return true;
    }


}
