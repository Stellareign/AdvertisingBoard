package ru.skypro.homework.factory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

@Component
public class HibernateSessionFactoryUtil {
    // фактори конструирует сессию, подгружает проперти
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) { // для того, чтобы фабрика не дублировалась при создании фактори, а если не налл, пользуем ту, что есть
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Image.class);
                configuration.addAnnotatedClass(Ad.class);
                configuration.addAnnotatedClass(Comment.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration
                                .getProperties());
                sessionFactory = configuration
                        .buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}

