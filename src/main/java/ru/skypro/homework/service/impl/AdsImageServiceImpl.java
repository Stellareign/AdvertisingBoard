package ru.skypro.homework.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.repository.AdsImageRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.AdsImageService;

import java.io.IOException;
import java.util.Optional;

@Data
@Slf4j
@Service
public class AdsImageServiceImpl implements AdsImageService {
    private final AdsImageRepository adsImageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    //     Мнетод сохраняет файл изображения для данного объявления.
//      id идентификатор объявления
//      file файл изображения для сохранения
//     вернуть сохраненное изображение в виде массива байтов
//     throws IOException, если при чтении файла возникает ошибка ввода-вывода
//     throws IllegalArgumentException, если файл пуст
//     throws RecordNotFoundException, если реклама не найдена
    @Override
    public AdsImage saveImage(byte[] imageBytes) throws IOException {
        if (imageBytes.length == 0) {
            throw new IllegalArgumentException("File is empty");
        }
        AdsImage adsImage =  new AdsImage(imageBytes);
        return adsImageRepository.save(adsImage);
    }
//     метод обновляет изображение для указанного объявления с заданным идентификатором.
//     id Идентификатор объявления, для которого необходимо обновить изображение.
//     file Новый загружаемый файл изображения.
//     return Обновленное изображение в виде массива байтов.
//     throws IOException Если при чтении файла возникает ошибка ввода-вывода.
//     throws IllegalArgumentException Если файл пуст или изображение с заданным идентификатором не найдено.
    @Override
    public AdsImage updateImage(Integer id, byte[] imageBytes) throws IOException {
        log.info("Вызван метод для обновления фотографии объявления с id = {}", id);
        if (imageBytes.length == 0) {
            throw new IllegalArgumentException("На входе пустой файл!");
        }
        AdsImage imageToSave = adsImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Старая картинка не обнаружена"));
        imageToSave.setImage(imageBytes);
        return adsImageRepository.save(imageToSave);
    }


//     Метод извлекает изображение для данного объявления по его идентификатору id.
//     return Данные изображения в виде массива байтов.
//     throws IllegalArgumentException Если изображение не найдено.

    @Transactional
    @Override
    public AdsImage getImage(int id) throws RecordNotFoundException {
        log.info("Метод извлекает изображение объявления с id = {}", id);
        Optional<AdsImage> adsImage = adsImageRepository.findById(id);
if (adsImage.isEmpty()) throw new RecordNotFoundException("Не удалось найти картинку товара с id =  "+id);
        return adsImage.get();
    }

    @Override
//     Удаляет картинку с указанным id
    public void deleteImageAds(int id) {
        log.info("Удаление картинки с id = " + id);
        adsImageRepository.deleteById(id);
    }
}
