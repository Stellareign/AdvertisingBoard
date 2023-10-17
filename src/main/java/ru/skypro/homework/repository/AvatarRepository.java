package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Avatar;


public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
    Avatar findAvatarById (String id);
    boolean existsAvatarById(String id);


}
