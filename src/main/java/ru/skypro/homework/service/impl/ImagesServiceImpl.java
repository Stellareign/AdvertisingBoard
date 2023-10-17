package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
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
    public Avatar createAvatar(MultipartFile image, Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(authentication.getName());

        if (user.getAvatarPath() != null) {
            String avatarId = user.getAvatarPath().replace("/image/", "");
            avatarId = avatarId.substring(0, avatarId.lastIndexOf("."));
            if (avatarRepository.existsAvatarById(avatarId)) {
                avatarRepository.delete(avatarRepository.findAvatarById(avatarId));
            }
        }
        if (!image.isEmpty()) {
            String type = image.getContentType().replace("image/", ".");

            String avatarName = UUID.randomUUID().toString();

            Avatar avatar = new Avatar();
            avatar.setImageData(image.getBytes());
            avatar.setId(avatarName);
            avatar.setFileType(type);
            avatar.setUser(user);
            avatarRepository.save(avatar);
            return avatar;
        }
        throw new IOException("Ошибка загрузки файла");
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
}
