package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {


    String createAvatarForUser(MultipartFile image) throws IOException;


}
