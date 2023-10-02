package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;
@Service
public interface UserService {
    boolean updatePassword (String currentPassword, String newPassword, User user);
}
