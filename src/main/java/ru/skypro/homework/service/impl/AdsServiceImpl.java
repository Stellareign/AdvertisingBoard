package ru.skypro.homework.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exceptions.RecordNotFoundException;

import ru.skypro.homework.models.Ad;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service

public class AdsServiceImpl implements AdsService {
    private AdsRepository adsRepository;
    public AdsServiceImpl(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }


    @Override
    public List<Ad> getAllAds() {
        return adsRepository.findAll();
    }

    @Override
    public Ad getAdsById(int adsId) {

        return adsRepository.findById(adsId).orElseThrow();
    }

    //    Получение информации об объявлении
    Optional<Ad> getAds(int id) {
        return adsRepository.findById(id);
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @Override
    public boolean deleteAdsById(int adsId) {

        Optional<Ad> optionalAds = adsRepository.findById(adsId);
        if (optionalAds.isPresent()) {
            adsRepository.deleteById(adsId);
            return true;
        } else {
            new RecordNotFoundException(String.valueOf(adsId));
            return false;
        }
    }

    @Override
    public Ad addAd(Ad ad) {
        return adsRepository.save(ad);
    }

    @Override
    public Ad editAdById(int id, String title, int price, String description)
            throws EntityNotFoundException {

        Optional optionalAd = adsRepository.findById(id);
        if (!optionalAd.isPresent()) {
            throwException(String.valueOf(id));

        }
        Ad existingAd = (Ad) optionalAd.get();

        existingAd.setTitle(title);
        existingAd.setPrice(price);
        existingAd.setAdsDescription(description);

        return adsRepository.save(existingAd);
    }

    private void throwException(String valueOf) {
    }

    //****************** репозиторий
    public void save(Ad ad) {
        adsRepository.save(ad);
    }
}

