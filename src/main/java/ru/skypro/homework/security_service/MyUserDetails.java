package ru.skypro.homework.security_service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.Set;

@Component
public  class MyUserDetails implements UserDetails {
    private final User user;
    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

/**
Метод {@link #getAuthorities()} возвращает коллекцию объектов, реализующих интерфейс
 {@link GrantedAuthority} ,
которые представляют собой разрешения пользователя.
Конкретно здесь метод возвращает коллекцию из одного объекта  {@link SimpleGrantedAuthority}.
Метод позволяет получить список разрешений, которые имеет пользователь в системе.
 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(user.getRole().name()));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}