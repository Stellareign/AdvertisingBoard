package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.authorization.Register;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.ImageService;
import ru.skypro.homework.service.interfaces.UserDTOFactory;
import ru.skypro.homework.service.interfaces.UserService;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * Класс содержит следующие методы:
     * {@link #checkUpdatePassword(UpdatePasswordDTO, String)} - проверка пароля при обновлении
     * <p>
     * {@link #getUserForGetController(String)} - загрузка юзера из БД сохранение в UserDTO
     * <p>
     * {@link #updateUser(String, UpdateUserDTO)} - обновление данных пользователя из запроса UpdateUserDTO
     * <p>
     * {@link #updateUserAvatar(Authentication, MultipartFile)} - обновление аватара пользователя
     * <p>
     * Приватные методы проверок:
     * {@link #checkUser(String)} - проверка наличия записи пользователя в БД
     * <p>
     * {@link #checkPhoneFormat(String)} - проверка соответствия телефона паттерну
     * <p>
     * {@link #checkPassword(String)} - проверка пароля на соблюдение условий
     * <p>
     * {@link #checkUsername(String)} - проверка логика пользователя на соответствие паттерну
     */

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserDTOFactory userDTOFactory;
    private final ImageService imageService;

//************************************************** МЕТОДЫ ************************************************************

    /**
     * Метод для изменения пароля.
     * Проверка нового пароля на соответствие требованиям и сохранение его в базу данных
     * На вход принимается объект класса {@link UpdateUserDTO} и строковый параметр
     *
     * @param username, являющийся логином (email) пользователя
     *                  Использует метод {@link #checkPassword(String password)}
     */
    @Override
    public boolean checkUpdatePassword(UpdatePasswordDTO updatePasswordDTO, String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        String password = encoder.encode(user.getPassword()); // я бы добавила ввод текущего пароля при смене пароля

        String newPassword = updatePasswordDTO.getNewPassword();

        if (!newPassword.equals(password) && checkPassword(newPassword)) {

            updatePasswordDTO.setCurrentPassword(password);
            updatePasswordDTO.setNewPassword(newPassword);

            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);

            return true;
        }
        log.info("Пароль не соответствует требованиям. Пароль должен быть длиной не менее 8-ми символов, " +
                "не состоять из пробелов и не должен совпадать с текущим паролем");
        return false;
    }

    //****************************************************************************************************************

    /**
     * Получение данных пользователя (контроллер)
     *
     * @param username - логин (email) пользователя
     */
    @Override
    public UserDTO getUserForGetController(String username) {
        User user = userRepository.findByUsername(username);
        return userDTOFactory.fromUserToUserDTO(user);
    }

//************************************************ ОБНОВЛЕНИЕ ЮЗЕРА ***************************************************

    /**
     * Обновление данных пользователя из запроса UpdateUserDTO и сохранение
     * изменений в базу данных
     *
     * @param username      - логин (email) пользователя
     *                      Принимаемый параметр и возвращаемый объект:
     * @param updateUserDTO класса {@link UpdateUserDTO}
     *                      Перед сохранением обновлённых данных указанный телефон проверяется на соответствие
     *                      требованиям в методе {@link #checkPhoneFormat(String)}
     *                      Обновлённая сущность сохраняется в методе
     *                      {@link ru.skypro.homework.repository.UserRepository#save(Object)}
     */
    @Override
    public UserDTO updateUser(String username, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findByUsername(username);

        if (checkPhoneFormat(updateUserDTO.getPhone())) {
            user.setFirstName(updateUserDTO.getFirstName());
            user.setLastName(updateUserDTO.getLastName());
            user.setPhone(updateUserDTO.getPhone());
            userRepository.save(user);

        }
        return userDTOFactory.fromUserToUserDTO(user);
    }

//***********************************************ПРОВЕРКА ЮЗЕРА ********************************************************

    /**
     * Проверка наличия записи пользователя в базе данных
     */
    @Override
    public boolean checkUser(String username) {
        return userRepository.findByUsername(username) != null;
    }

//**************************************** СОХРАНЕНИЕ ЮЗЕРА  ПРИ РЕГИСТРАЦИИ ********************************************

    /**
     * Сохранение пользователя в базу данных после ввода данных при регистрации
     * из метода
     * {@link #createUserFromRegister(Register register)}
     */
    @Override
    public void saveRegisterUser(Register register) {
        userRepository.save(createUserFromRegister(register));
    }

//******************************************* ОБНОВЛЕНИЕ ЮЗЕРА В БД ****************************************************

    /**
     * Создание учётных данных пользователя при регистрации.
     * Применяется далее в методе {@link #saveRegisterUser(Register register)}
     * Для проверки использует методы проверки параметров:
     * - логина {@link #checkUsername(String username)},
     * - телефона {@link #checkPhoneFormat(String phone)},
     * - пароля{@link #checkPassword(String password)},
     * Возвращает объект класса {@link User}.
     */
    private User createUserFromRegister(Register register) {

        String pass = encoder.encode(register.getPassword()); // шифрование пароля
        if (!checkPassword(register.getPassword()) && !checkUsername(register.getUsername())
                && !checkPhoneFormat(register.getPhone())) {
            log.info("Ошибка при вводе данных для  регистрации!");
        }
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

    //****************************************** ОБНОВЛЕНИЕ АВАТАРА ЮЗЕРА  *************************************************

    /**
     * Метод обновления аватара пользователя
     * Принимает на вход два параметра
     *
     * @param image          - изображение
     * @param authentication - текущего пользователя
     * @return объект класса {@link UserDTO}
     * @throws IOException
     */
    @Override
//    @Transactional
    public UserDTO updateUserAvatar(Authentication authentication, MultipartFile image) throws IOException {
        User user = userRepository.findByUsername(authentication.getName());
        Avatar avatar = imageService.createAvatar(image, authentication.getName());
        user.setAvatarPath(avatar.getImagePath());
        user.setUserAvatar(avatar);
        userRepository.save(user);

        return userDTOFactory.fromUserToUserDTO(user);
    }


//****************************************** ПРОВЕРКА ВВОДИМЫХ ДАННЫХ *************************************************

    private boolean checkPassword(String password) {
        if (password.length() >= 8 && !password.isBlank()) {
            log.info("Пароль соответствует требованиям!");
            return true;
        } log.info("Пароль не соответствует требованиям! Пароль не должен состоять из пробелов, длина" +
                    "пароля должны быть не менее 8-ми символов!");
        return false;
    }

    private boolean checkPhoneFormat(String phone) {
        if (phone.matches("\\+7\\s?\\(\\d{3}\\)\\s?\\d{3}-\\d{2}-\\d{2}")) {
            log.info("Формат телефона верный.");
            return true;
        } log.info("Укажите номер телефона в формате +7(ХХХ)ХХХ-ХХ-ХХ!");
        return false;
    }

    private boolean checkUsername(String username) {
        if (username.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            log.info("Логин указан верно");
            return true;
        } log.info("Проверьте указанный email. Логин должен быть указан в формате user@user.us!");
        return false;
    }
}

