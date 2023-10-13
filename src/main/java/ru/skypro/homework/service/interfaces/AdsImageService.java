package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.exceptions.RecordNotFoundException;

import java.io.IOException;
@Service
public interface AdsImageService {

    //     Мнетод сохраняет файл изображения для данного объявления.
//      id идентификатор объявления
//      file файл изображения для сохранения
//     вернуть сохраненное изображение в виде массива байтов
//     throws IOException, если при чтении файла возникает ошибка ввода-вывода
//     throws IllegalArgumentException, если файл пуст
//     throws RecordNotFoundException, если реклама не найдена
    AdsImage saveImage(byte[] imageBytes) throws IOException;

    //     метод обновляет изображение для указанного объявления с заданным идентификатором.
    //     id Идентификатор объявления, для которого необходимо обновить изображение.
    //     file Новый загружаемый файл изображения.
    //     return Обновленное изображение в виде массива байтов.
    //     throws IOException Если при чтении файла возникает ошибка ввода-вывода.
    //     throws IllegalArgumentException Если файл пуст или изображение с заданным идентификатором не найдено.
    AdsImage updateImage(Integer id, byte[] imageBytes) throws IOException;

    @Transactional
    AdsImage getImage(int id) throws RecordNotFoundException;

    //     Удаляет картинку с указанным id
    void deleteImageAds(int id);
}
