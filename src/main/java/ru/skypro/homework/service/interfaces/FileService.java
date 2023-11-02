package ru.skypro.homework.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    /*
     * Копирование файла картинки. Входной поток получаем
     * из метода {@link Files#newInputStream(Path, OpenOption...)}
     * @param imagePath путь и название файла с картинкой
     * @return image - массив байт картинки
     * @throws IOException ошибка ввода - вывода
     * @see Path#of(URI)
     */
    byte[] downloadImage(String imagePath) throws IOException;

    /*
     * Загрузка на сервер файла картинки. Входной поток получаем методом
     * {@link MultipartFile#getInputStream()}. Выходной поток получаем методом
     * {@link Files#newOutputStream(Path, OpenOption...)}
     * @param image файл картинки
     * @param filePath путь к файлу на сервере
     * @throws IOException ошибка ввода - вывода
     * @see Files#deleteIfExists(Path)
     * @see Files#createDirectories(Path, FileAttribute[])
     */
    void uploadImage(MultipartFile image, Path filePath) throws IOException;
}
