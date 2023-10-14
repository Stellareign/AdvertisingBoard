package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;


public interface ImageRepository extends JpaRepository<Avatar, Integer> {
   Avatar findAvatarById(int id);
   Avatar findAvatarByUser(User user);
   Avatar findAvatarByAvatarName(String name);

}
