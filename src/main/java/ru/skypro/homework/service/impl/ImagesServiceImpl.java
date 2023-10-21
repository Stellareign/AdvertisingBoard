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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.springframework.util.StringUtils.getFilenameExtension;

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
    public Avatar createAvatar(MultipartFile image, String userId) throws IOException {
        User user = userRepository.findByUsername(userId);

        if (user.getAvatarPath() != null) {
            String avatarId = user.getUserAvatar().getId();
            Avatar oldAvatar = avatarRepository.findAvatarById(avatarId);
            Path deletePath = Path.of(user.getAvatarPath());
            Files.deleteIfExists(deletePath);
            log.info("Старая аватарка удалена.");
            if (oldAvatar != null) {
                user.setUserAvatar(null);
                user.setAvatarPath(null);
                userRepository.save(user);
                avatarRepository.delete(oldAvatar);
            }
        } else {
            log.info("Текущий аватар отсутствует");
        }
        Avatar newAvatar = new Avatar();
        try {
            String avatarId = UUID.randomUUID().toString();
            byte[] bytes = image.getBytes();
            newAvatar.setId(avatarId);
            newAvatar.setImageData(bytes);
            newAvatar.setImagePath(saveImage(image, avatarId, user));
            avatarRepository.save(newAvatar);
        } catch (IOException e) {
            log.info("Ошибка загрузки файла");
        }

        return newAvatar;
    }

    private String saveImage(MultipartFile file, String fileName, User user) throws IOException {
        Path imagePath = Path.of("/images/" + fileName + "."
                + getFilenameExtension(file.getOriginalFilename()));

        String destination = imagePath.toString();
        Files.createDirectories(imagePath.getParent());

        File newFile = new File(imagePath.toUri());
        file.transferTo(newFile);
        return destination;
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
     *
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
