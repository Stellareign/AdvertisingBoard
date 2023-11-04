package ru.skypro.homework.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {

    byte[] downloadImage(String imagePath) throws IOException;

    void uploadImage(MultipartFile image, Path filePath) throws IOException;
}
