package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.ImageService;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor

/**
 *@Transactional -  указывает на то, что метод или класс должен быть выполнен в транзакции.
 * Транзакция -  единица работы с БД>, которая должна быть выполнена целиком или не выполнена вообще.
 * @Transactional позволяет автоматически управлять транзакциями и обеспечить целостность данных при
 * работе с базой данных.
 */

public class ImagesServiceImpl implements ImageService {


    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;

    @Value("${path.to.image.folder}")
    private String imagePath; // адрес картинки

    @Override
//    @Transactional
    /**
     * Метод загружает аватар пользователя (изображение)
     * @param image - загружаемый файл
     * @param authentication - аутентификаия текущего пользователя
     * @return - возвращает сохранённую картику как резкльтат выполнения метода
     * Метод может выкидывать исключение:
     * @throws IOException
     */
    public Avatar createAvatar(MultipartFile image, String userId)  {
        User user = userRepository.findByUsername(userId);

        if (user.getAvatarPath() != null && !user.getAvatarPath().isEmpty()) {
            String avatarId = user.getUserAvatar().getId();
            Avatar oldAvatar = avatarRepository.findAvatarById(avatarId);
            if (oldAvatar != null) {
                user.setUserAvatar(null);
                user.setAvatarPath(null);
                userRepository.save(user);
                avatarRepository.delete(oldAvatar);

            }
        }else {log.info("Текущий аватар отсутствует");}
        Avatar newAvatar = new Avatar();
        try {
            String avatarId = UUID.randomUUID().toString();
            byte[] bytes = image.getBytes();
            newAvatar.setId(avatarId);
            newAvatar.setImageData(bytes);
            newAvatar.setImagePath(imagePath + avatarId + image.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        avatarRepository.save(newAvatar);
        return newAvatar;
    }

    @Override
    /**
     * Удаление старого аватара после обновления
     */
    public void deleteImage(String avatarId) {
        Avatar avatar = avatarRepository.findAvatarById(avatarId);
        if (avatar != null) {
            avatarRepository.delete(avatar);
        }
    }

    /**
     * Извлечение аватара из БД через пользователя
     * @param authentication
     * @return
     * @throws RecordNotFoundException
     */
    @Override
    public byte[] getAvatar(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        String avatarId = user.getUserAvatar().getId();
        if (avatarRepository.existsAvatarById(avatarId)) {
            return avatarRepository.findAvatarById(avatarId).getImageData();
        }
        throw new RecordNotFoundException("Сейчас у пользователя нет аватара");
    }
}
