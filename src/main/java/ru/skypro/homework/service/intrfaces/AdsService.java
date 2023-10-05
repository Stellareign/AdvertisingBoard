package ru.skypro.homework.service.intrfaces;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public interface AdsService {
    List<Ad> getAllAds();

    Optional<Ad> getAdById(int adsId);

    //+++++++++++++++++++++++++++++++++++++++++
    boolean deleteAdsById(int adsId);


    Ad addAd(Ad ad);

    Ad editAdById(int id, CreateOrUpdateAdDTO updateAd)
            throws EntityNotFoundException;

    Ad editImageAdById(int id, String imagePath)
            throws EntityNotFoundException;

//    List<Ad> getAllAdsByUser(int userId);

    List<Ad> getAllAdsByUser(String userLogin);
}
