package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.AdsImage;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;


@Service
public interface AdsService {

    ExtendedAdDTO getAdById(int adsId);

    //+++++++++++++++++++++++++++++++++++++++++
    void deleteAdsById(int adsId);



    Ad createAd(String title, int price, String description, MultipartFile image) throws IOException;

    Ad editAdById(int id, CreateOrUpdateAdDTO updateAd)
            throws EntityNotFoundException;


    AdsDTO getAdsDTO();

//       Обновляет изображение
//    с заданным
//    идентификатором.
//
//             * @param id Идентификатор изображения, которое необходимо обновить.
//     * @param image Файл изображения, который необходимо обновить.
//     * @return Обновленное изображение в виде массива байтов.
//            * @throws IOException Если есть ошибка при чтении файла изображения.
//            * @throws IllegalArgumentException Если файл изображения пуст.
    AdsImage updateImage(int id, MultipartFile image) throws IOException;

    AdEntity editImageAdById(int id, AdsImage image);

    AdsDTO getAllAdsByUser(Authentication authentication);
}
