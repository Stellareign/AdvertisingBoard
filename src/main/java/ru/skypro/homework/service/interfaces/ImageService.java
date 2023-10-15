package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Avatar;

import java.io.IOException;

@Service
public interface ImageService {
    /**
     * Метод загружает аватар пользователя (изображение)
     * @param image - загружаемый файл
     * @param authentication - аутентификаия текущего пользователя
     * @return - возвращает сохранённую картику как резкльтат выполнения метода
     * Метод может выкидывать исключение:
     * @throws IOException
     */
    Avatar createAvatar(MultipartFile image, Authentication authentication) throws IOException;

}
