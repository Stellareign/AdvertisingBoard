package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.ImageService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImageService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public Avatar updateUsersAvatar (MultipartFile image, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Avatar avatar = new Avatar();
        avatar.setImageData(image.getBytes());
        imageRepository.save(avatar);
        user.setUserAvatar(avatar);
        userRepository.save(user);
        return avatar;
    }

}
