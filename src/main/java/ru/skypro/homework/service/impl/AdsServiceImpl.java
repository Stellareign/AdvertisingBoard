package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.RecordNotFoundException;

import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import javax.persistence.EntityNotFoundException;
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
    public Ad getAdById(int adsId) {

        return adsRepository.findById(adsId).orElseThrow();
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
    public Ad addAd(String title,   // 'заголовок объявления'
                    int price,               // 'цена объявления'
                    String image,            //'ссылка на картинку объявления'
                    User author)  {
        Ad ad = new Ad(title, price, image, author);
        return adsRepository.save(ad);
    }

    @Override
    public Ad editAdById(int id, CreateOrUpdateAdDTO updateAd)
            throws EntityNotFoundException {
        Optional<Ad> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            throwException(String.valueOf(id));
        }
        Ad existingAd = optionalAd.get();

        existingAd.setTitle(updateAd.getTitle());
        existingAd.setPrice(updateAd.getPrice());
        existingAd.setDescription(updateAd.getDescription());

        return adsRepository.save(existingAd);
    }
    @Override
    public Ad editImageAdById(int id, String imagePath)
            throws EntityNotFoundException {
        Optional<Ad> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty())        throwException(String.valueOf(id));
        Ad existingAd = optionalAd.get();
        existingAd.setImage(imagePath);
        return adsRepository.save(existingAd);
    }
    @Override
    public List<Ad> getAllAdsByUser(int userId) {
        return adsRepository.findAll()
                .stream()
                .filter(e -> e.getAuthor().getId() == userId)
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
    private void throwException(String valueOf) {
    }

}

