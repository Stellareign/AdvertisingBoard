package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.interfaces.FileService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    /**
     * Копирование файла картинки. Входной поток получаем
     * из метода {@link Files#newInputStream(Path, OpenOption...)}
     * @param imagePath путь и название файла с картинкой
     * @return image - массив байт картинки
     * @throws IOException ошибка ввода - вывода
     * @see Path#of(URI)
     */
    @Override
    public byte[] downloadImage(String imagePath) throws IOException {
        Path path = Path.of(imagePath);
        try (InputStream is = Files.newInputStream(path)) {
            byte[] image = is.readAllBytes();
            log.info("Image was downloaded successfully.");
            return image;
        }
    }

    /**
     * Загрузка на сервер файла картинки. Входной поток получаем методом
     * {@link MultipartFile#getInputStream()}. Выходной поток получаем методом
     * {@link Files#newOutputStream(Path, OpenOption...)}
     * @param image файл картинки
     * @param filePath путь к файлу на сервере
     * @throws IOException ошибка ввода - вывода
     * @see Files#deleteIfExists(Path)
     * @see Files#createDirectories(Path, FileAttribute[])
     */
    @Override
    public void uploadImage(MultipartFile image, Path filePath) throws IOException {
        Files.deleteIfExists(filePath);
        Files.createDirectories(filePath.getParent());
        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            log.info("Image was uploaded successfully.");
        }
    }
}