package ru.skypro.homework.service.interfaces;


import ru.skypro.homework.dto.authorization.Register;

public interface AuthService {

    boolean login(String userName, String password);

    boolean register(Register register);
}
