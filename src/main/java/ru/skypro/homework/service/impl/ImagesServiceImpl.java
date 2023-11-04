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



public class ImagesServiceImpl implements ImageService {


    private final UserRepository userRepository;
    private final FileService fileService;


    @Value("${path.to.avatar.folder}")
    private String pathToImage;

    /**
     *  Удаление старого аватара перед обновлением
     * @param authentication
     */
    @Override
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

    /**
     * @param image
     * @return
     * @throws IOException
     */
    @Override
    public String saveImage(MultipartFile image, int id) throws IOException {
        Path imagePath = Path.of(pathToImage + "avatar" + id + "."
                + getFilenameExtension(image.getOriginalFilename()));
        fileService.uploadImage(image, imagePath);
        return imagePath.toString();
    }


    /**
     * Получение файла аватара
     * @param authentication
     * @return массив байтов
     * @throws RecordNotFoundException
     */
    @Override
    public byte[] getAvatar(Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(authentication.getName());
        if (!user.getAvatarPath().isEmpty()) {
            Path path = Path.of("/users/image/"+user.getId());
            return Files.readAllBytes(path);
        }
        throw new RecordNotFoundException("Сейчас у пользователя нет аватара");
    }
}
