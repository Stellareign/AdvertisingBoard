package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.models.Ad;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public interface AdsService {
    List<Ad> getAllAds();

    Ad getAdsById(int adsId);

    //+++++++++++++++++++++++++++++++++++++++++
    boolean deleteAdsById(int adsId);

    Ad addAd(Ad ad);

    Ad editAdById(int id, String title, int price, String description)
            throws EntityNotFoundException;
}
