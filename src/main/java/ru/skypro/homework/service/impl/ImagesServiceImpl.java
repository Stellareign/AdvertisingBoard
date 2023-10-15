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
    private final AvatarRepository imageRepository;

    @Value("${path.to.image.folder}")
    private String imagePath; // адрес картинки

    @Override
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
        Avatar currentAvatar = user.getUserAvatar();
        String avatarName = UUID.randomUUID().toString();
        Avatar newAvatar;
        if (currentAvatar != null) {
            currentAvatar.setImageData(image.getBytes());
            currentAvatar.setId(avatarName);
            currentAvatar.setUser(user);
           newAvatar = imageRepository.save(currentAvatar);
        } else {
            Avatar avatar = new Avatar();
            avatar.setImageData(image.getBytes());
            avatar.setId(avatarName);
            avatar.setUser(user);
            newAvatar = imageRepository.save(avatar);
        }

//        Path filePath = Path.of(imagePath, UUID.randomUUID().toString());
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//        Files.write(filePath, image.getBytes());
        return newAvatar;
    }

//    @Override
//    /**
//     * Удаление аватара после замены
//     * @param avatarPath
//     * @throws IOException
//     */
//    public void deleteImage(Avatar avatar) throws IOException {
//        if (avatar != null) {
//            Path filePath = Path.of(avatar.getAvatarPath());
//            Files.deleteIfExists(filePath);
//            imageRepository.delete(avatar);
//        }
//    }

//
//    /**
//     * user.dir - системное свойство, которое возвращает путь к текущей рабочей директории.
//     * В данном случае он используется для получения пути к директории, где будет сохранён загруженный файл.
//     * <p>
//     * File.separator - системно-зависимый разделитель директорий в пути к файлу. Например,
//     * в Windows это будет обратный слэш (\), а в Unix/Linux - прямой (/).
//     * <p>
//     * images - название директории, кот. будет создана в текущей рабочей директории
//     * для хранения загруженных файлов.
//     */
//    private final String filePath = System.getProperty("user.dir") + File.separator + "images";
//
//    //********************************************************************************************************************
//    @Override
//    public String createAvatarForUser(MultipartFile image, Authentication authentication) throws IOException {
//        Avatar avatarPath = new Avatar();
//        try {
//            String imageName = UUID.randomUUID() + fileType(image);
//            createDirectories(Paths.get(filePath));
//            image.transferTo(new File(filePath + File.separator + imageName));
//
//            avatarPath.setAvatarName(imageName);
//            avatarPath.setImageData(image.getBytes());
//            avatarPath.setUser(userRepository.findByUsername(authentication.getName()));
//            imageRepository.save(avatarPath);
//
//            log.info("Аватар создан:: {}", imageName);
//        } catch (IOException e) {
//            log.error("Ошибка обновления аватара", avatarPath.getId());
//        }
//        return avatarPath.getAvatarName();
//    }
//
////**********************************************************************************************************************
//
//
//    /**
//     * Метод {@link #fileType(MultipartFile)} принимает в качестве параметра объект класса {@link MultipartFile},
//     * который представляет загружаемый файл. Внутри метода происходит получение типа файла с помощью
//     * метода getContentType(), который возвращает строку с типом файла. Далее, с помощью метода assert проверяется,
//     * что тип файла не равен null. Если это так, то выполняется замена подстроки "image/" на "."
//     * с помощью метода replace().
//     * В результате получается строка, содержащая расширение файла (например, ".jpg" или ".png"), которая
//     * возвращается в виде результата метода.
//     */
//    private String fileType(MultipartFile image) {
//        String type = image.getContentType();
//        assert type != null;
//        type = type.replace("image/", ".");
//        return type;
//    }
}
