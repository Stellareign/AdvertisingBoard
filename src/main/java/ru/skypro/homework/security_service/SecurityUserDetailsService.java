package ru.skypro.homework.security_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    // Внедряем зависимость UserRepository для доступа к базе пользователей
    private UserRepository userRepository;

    @Override
    // Реализация метода loadUserByUsername,
    // который принимает строку с именем пользователя.
    public UserDetails loadUserByUsername(String username)  {
        // Ищем пользователя в базе данных с указанным именем пользователя
        User user = userRepository.findByUsername(username);
        // Если пользователь не найден, выбрасываем исключение
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        // Возвращаем найденного пользователя, завёрнутого в объект
        // класса MyUserDetails. Он отвечает за предоставление
        // пользовательской информации при аутентификации.
        return new MyUserDetails(user);
    }
}
