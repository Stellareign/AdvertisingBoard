package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.ImageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.Files.createDirectories;

@Slf4j
@Service
@RequiredArgsConstructor

@Transactional
/**
 *@Transactional -  указывает на то, что метод или класс должен быть выполнен в транзакции.
 * Транзакция -  единица работы с БД>, которая должна быть выполнена целиком или не выполнена вообще.
 * @Transactional позволяет автоматически управлять транзакциями и обеспечить целостность данных при
 * работе с базой данных.
 */

public class ImagesServiceImpl implements ImageService {
    /**
     * Класс содержит следующие методы:
     * {@link #createAvatarForUser(MultipartFile)} - создание и сохранение аватара юзера
     *
     * {@link #fileType(MultipartFile)} - создание расширения файла
     */

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    /**
     * user.dir - системное свойство, которое возвращает путь к текущей рабочей директории.
     * В данном случае он используется для получения пути к директории, где будет сохранён загруженный файл.
     * <p>
     * File.separator - системно-зависимый разделитель директорий в пути к файлу. Например,
     * в Windows это будет обратный слэш (\), а в Unix/Linux - прямой (/).
     * <p>
     * images - название директории, кот. будет создана в текущей рабочей директории
     * для хранения загруженных файлов.
     */
    private final String filePath = System.getProperty("user.dir") + File.separator + "images";

    //********************************************************************************************************************
    @Override
    public String createAvatarForUser(MultipartFile image) throws IOException {
        Avatar avatar = new Avatar();
        try {
            String imageName = UUID.randomUUID() + fileType(image);
            createDirectories(Paths.get(filePath));
            image.transferTo(new File(filePath + File.separator + imageName));

            avatar.setAvatarName(imageName);
            avatar.setImageData(image.getBytes());
            imageRepository.save(avatar);

            log.info("Аватар создан:: {}", imageName);
        } catch (IOException e) {
            log.error("Ошибка обновления аватара", avatar.getId());
        }
        return avatar.getAvatarName();
    }

//**********************************************************************************************************************


    /**
     * Метод {@link #fileType(MultipartFile)} принимает в качестве параметра объект класса {@link MultipartFile},
     * который представляет загружаемый файл. Внутри метода происходит получение типа файла с помощью
     * метода getContentType(), который возвращает строку с типом файла. Далее, с помощью метода assert проверяется,
     * что тип файла не равен null. Если это так, то выполняется замена подстроки "image/" на "."
     * с помощью метода replace().
     * В результате получается строка, содержащая расширение файла (например, ".jpg" или ".png"), которая
     * возвращается в виде результата метода.
     */
    private String fileType(MultipartFile image) {
        String type = image.getContentType();
        assert type != null;
        type = type.replace("image/", ".");
        return type;
    }
}
