package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;

@EnableJpaRepositories
@Service
public interface UserRepository extends JpaRepository<User, Integer> {
    User getById(int id);
}
