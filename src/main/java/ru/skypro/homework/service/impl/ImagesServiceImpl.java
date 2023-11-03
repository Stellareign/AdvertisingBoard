package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.FileService;
import ru.skypro.homework.service.interfaces.ImageService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.springframework.util.StringUtils.getFilenameExtension;

@Slf4j
@Service
@RequiredArgsConstructor

/*
 *@Transactional -  указывает на то, что метод или класс должен быть выполнен в транзакции.
 * Транзакция -  единица работы с БД>, которая должна быть выполнена целиком или не выполнена вообще.
 * @Transactional позволяет автоматически управлять транзакциями и обеспечить целостность данных при
 * работе с базой данных.
 */

public class ImagesServiceImpl implements ImageService {


    private final UserRepository userRepository;
    private final FileService fileService;


    @Value("${path.to.avatar.folder}")
    private String pathToImage;

    @Override
    /** Удаление старого аватара перед обновлением
     * @param authentication
     */
    public void deleteOldAvatar(Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(authentication.getName());

        if (user.getAvatarPath() != null && !user.getAvatarPath().isEmpty()) {
            Files.deleteIfExists(Path.of(user.getAvatarPath()));
            log.info("Старая аватарка удалена.");
            user.setAvatarPath(null);
            userRepository.save(user);
        } else {
            log.info("Текущий аватар отсутствует");
        }
    }

    @Override
    /**Сохранение картинки в указанную директорию
     * @param image
     * @return
     * @throws IOException
     */
    public String saveImage(MultipartFile image, int id) throws IOException {
        Path imagePath = Path.of(pathToImage + "avatar" + id + "."
                + getFilenameExtension(image.getOriginalFilename()));
        fileService.uploadImage(image, imagePath);
        return imagePath.toString();
    }


    @Override
    /**
     * Получение файла аватара
     * @param authentication
     * @return
     * @throws RecordNotFoundException
     */
    public byte[] getAvatar(Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(authentication.getName());
        if (!user.getAvatarPath().isEmpty()) {
            //           Path path = Path.of(user.getAvatarPath());
            Path path = Path.of("/users/image/" + user.getId());
            return Files.readAllBytes(path);
        }
        throw new RecordNotFoundException("Сейчас у пользователя нет аватара");
    }
}
