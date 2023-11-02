package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exceptions.RecordNotFoundException;

import java.io.IOException;

@Service
public interface ImageService {
//

    /**
     * @throws IOException
     */
    void deleteOldAvatar(Authentication authentication) throws IOException;

    /**
     * @param image
     * @return
     * @throws IOException
     */
    String saveImage(MultipartFile image, int id) throws IOException;


    /**
     * Извлечение аватара из БД через пользователя
     *
     * @param authentication
     * @return
     * @throws RecordNotFoundException
     */
    byte[] getAvatar(Authentication authentication) throws IOException;
}
