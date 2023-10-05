package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.ImageService;

import java.util.Optional;
@Service
public class ImageServiceImpl implements ImageService {
    private final UserRepository userRepository;
    public ImageServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void changeAvatarPath(int userId, String filePath){
      Optional<User> oprionslUser = userRepository.findById(userId);
      if(!oprionslUser.isPresent()){
          throw new NotFoundException("Запись " + userId + " отсутствует"); // проверка нужна??
      }
      User user = (User) oprionslUser.get();
      user.setImage(filePath);
      userRepository.save(user);
    }
}
