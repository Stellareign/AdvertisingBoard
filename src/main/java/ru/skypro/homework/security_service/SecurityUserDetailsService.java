package ru.skypro.homework.security_service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.AuthUserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.UserDTOFactory;
import ru.skypro.homework.service.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDTOFactory userDTOFactory;
    private final UserService userService;


    /***
     * Реализация метода loadUserByUsername,
     *который принимает строку с именем пользователя.
     * @param username the username identifying the user whose data is required.
     * Ищем пользователя в базе данных с указанным именем пользователя:
     * User user = userRepository.findByUsername(username).
     * Если пользователь не найден, выбрасываем исключение
     * @throws UsernameNotFoundException
     * Возвращаем найденного пользователя, завёрнутого в объект класса MyUserDetails.
     * Он отвечает за предоставление:
     * @return new MyUserDetails(user);
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        if (!userService.checkUser(username)) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserDetails(creteAuthUserDTOForUserDetails(username));
    }

    private AuthUserDTO creteAuthUserDTOForUserDetails(String username) {
        User user = userRepository.findByUsername(username);
        return userDTOFactory.fromUserToAuthUserDTO(user);
    }
}
