package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.MapperUtil;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.interfaces.AdsService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final MapperUtil mapperUtil;

    public AdsServiceImpl(AdsRepository adsRepository, MapperUtil mapperUtil) {
        this.adsRepository = adsRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public AdsDTO getAdsDTO() {
        List<AdEntity> adList = adsRepository.findAll();
        return new AdsDTO(adList.size(), adList);
    }

    @Override
    public ExtendedAdDTO getAdById(int adsId) {
        Optional<AdEntity> optionalAds = adsRepository.findById(adsId);
        if (optionalAds.isEmpty())       {
            throw new RecordNotFoundException("Не удалось найти объявление с id =  "+adsId);
        }
        return mapperUtil.createExtendedAdDTO(optionalAds.get());
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @Override
    public void deleteAdsById(int adsId) {
        Optional<AdEntity> optionalAds = adsRepository.findById(adsId);
        if (optionalAds.isPresent())
            adsRepository.deleteById(adsId);
    }

    @Override
    public Ad createAd(CreateOrUpdateAdDTO createAdDTO, MultipartFile image) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = mapperUtil.getMapper().map(authentication, User.class);
 //       User currentUser = userRepository.findById(2).get();// временно, пока не разберусь с Authentication
        AdEntity newAd = mapperUtil.createAdFromDTO(createAdDTO, image, currentUser);
        adsRepository.save(newAd);
        return mapperUtil.getMapper().map(newAd, Ad.class);
    }

    @Override
    public Ad editAdById(int id, CreateOrUpdateAdDTO updateAd) {
        Optional<AdEntity> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            throw new RecordNotFoundException("Не удалось найти объявление с id =  "+id);
        }
        AdEntity existingAd = optionalAd.get();
        existingAd.setTitle(updateAd.getTitle());
        existingAd.setPrice(updateAd.getPrice());
        existingAd.setDescription(updateAd.getDescription());
        adsRepository.save(existingAd);
        return mapperUtil.getMapper().map(existingAd, Ad.class);
    }
//       Обновляет изображение
//    с заданным
//    идентификатором.
    @Override
    public AdEntity updateImage(int id, MultipartFile image) throws IOException {
        Optional<AdEntity> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            throw new RecordNotFoundException(String.valueOf(id));
        }
        AdEntity existingAd = optionalAd.get();
        Path filePath = Path.of("/images/ad_" + image.getOriginalFilename() + "."
                + StringUtils.getFilenameExtension(image.getOriginalFilename()));
        mapperUtil.uploadImage(image, filePath);
        existingAd.setImage(filePath.toString());
        adsRepository.save(existingAd);
        return existingAd;
    }

    @Override
    public AdsDTO getAllAdsByUser(Authentication authentication) {
        String currentUserName = authentication.getName();
        List<AdEntity> adList = adsRepository.findAll()
                .stream()
                .filter(e -> e.getAuthor().getUsername().equals(currentUserName))
                .collect(Collectors.toList());
        return new AdsDTO(adList.size(), adList);
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

