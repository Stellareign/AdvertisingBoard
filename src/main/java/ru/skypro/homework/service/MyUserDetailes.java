package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;

import java.nio.file.attribute.UserPrincipal;
@Service
public  class MyUserDetailes implements UserPrincipal {
    public MyUserDetailes(User user) {
        this.user = user;
    }

    private User user;


    @Override
    public String getName() {
       return user.getUsername();
    }
}
