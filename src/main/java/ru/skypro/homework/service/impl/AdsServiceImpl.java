package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.exceptions.RecordNotFoundException;

import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;

    public AdsServiceImpl(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }


    @Override
    public List<Ad> getAllAds() {
        return adsRepository.findAll();
    }

    @Override
    public Optional<Ad> getAdById(int adsId) {
        return adsRepository.findById(adsId);
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @Override
    public void deleteAdsById(int adsId) {

        Optional<Ad> optionalAds = adsRepository.findById(adsId);
        if (optionalAds.isPresent())
            adsRepository.deleteById(adsId);
    }

    @Override
    public Ad addAd(Ad ad) {
        adsRepository.save(ad);
        return ad;
    }

    @Override
    public Ad editAdById(int id, CreateOrUpdateAdDTO updateAd){
        Optional<Ad> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            new RecordNotFoundException(String.valueOf(id));
        }
        Ad existingAd = optionalAd.get();
        existingAd.setTitle(updateAd.getTitle());
        existingAd.setPrice(updateAd.getPrice());
        existingAd.setDescription(updateAd.getDescription());
        adsRepository.save(existingAd);
        return existingAd;
    }

    @Override
    public Ad editImageAdById(int id, String image) {
        Optional<Ad> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            new RecordNotFoundException(String.valueOf(id));
        }
        Ad existingAd = optionalAd.get();
        existingAd.setImage(image);
        adsRepository.save(existingAd);
        return existingAd;
    }

    @Override
    public List<Ad> getAllAdsByUser(String userLogin) {
        return adsRepository.findAll()
                .stream()
                .filter(e -> e.getAuthor().getUsername().equals(userLogin))
                .collect(Collectors.toList());
    }

    /*
    из разбора с Волковым
    .stream()
                .filter(e -> e.getValue().getIngredients.stream()
                .anyMatch(i -> i.getTitle()/equals(ingrediente.getTitle())))
                .map(e -> RecipeDTO.from(e.getKey(), e.getValue())))
                .collect(Collectors.toList());
     */

}

