package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public interface AdsService {
    List<Ad> getAllAds();

    Ad getAdById(int adsId);

    //+++++++++++++++++++++++++++++++++++++++++
    boolean deleteAdsById(int adsId);

    Ad addAd(String title,   // 'заголовок объявления'
             int price,               // 'цена объявления'
             String image,            //'ссылка на картинку объявления'
             User author);

    Ad editAdById(int id, CreateOrUpdateAdDTO updateAd)
            throws EntityNotFoundException;

    Ad editImageAdById(int id, String imagePath)
            throws EntityNotFoundException;

    List<Ad> getAllAdsByUser(int userId);

}
