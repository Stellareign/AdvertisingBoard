<<<<<<<< HEAD:src/main/java/ru/skypro/homework/service/intrfaces/AuthService.java
package ru.skypro.homework.service.intrfaces;
========
package ru.skypro.homework.service.interfaces;
>>>>>>>> RuAna:src/main/java/ru/skypro/homework/service/interfaces/AuthService.java

import ru.skypro.homework.dto.authorization.Register;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);
}
