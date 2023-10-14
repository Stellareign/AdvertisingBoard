package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Avatar;

import java.io.IOException;

@Service
public interface ImageService {

    Avatar updateUsersAvatar (MultipartFile image, Authentication authentication) throws IOException;
}
