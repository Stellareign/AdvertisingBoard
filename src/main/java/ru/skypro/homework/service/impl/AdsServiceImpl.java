package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.MapperUtil.MapperUtilAds;
import ru.skypro.homework.service.interfaces.AdsService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final MapperUtilAds mapperUtil;

    public AdsServiceImpl(AdsRepository adsRepository, UserRepository userRepository, MapperUtilAds mapperUtil) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public AdsDTO getAdsDTO() {
        List<Ad> adList = mapperUtil.convertListAdEntityToAd(adsRepository.findAll());
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
    public Ad createAd(CreateOrUpdateAd createAdDTO,
                       MultipartFile image
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());
        AdEntity newAd = mapperUtil.createAdFromDTO(createAdDTO, image, currentUser);
        adsRepository.save(newAd);
        return mapperUtil.getMapper().map(newAd, Ad.class);
    }
    @Override
    public Ad createAd2(CreateOrUpdateAd createAdDTO) throws IOException {
       Optional<User> optionalUser = userRepository.findById(1);// временно, пока не разберусь с Authentication
        if (optionalUser.isEmpty()){
            throw new RecordNotFoundException("User not found");
        }
       User currentUser = optionalUser.get();
        AdEntity newAd = mapperUtil.createAdFromDTO2(createAdDTO, currentUser);
        adsRepository.save(newAd);
        return mapperUtil.getMapper().map(newAd, Ad.class);
    }

    @Override
    public Ad editAdById(int id, CreateOrUpdateAd updateAd) {
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
        List<AdEntity> adEntityList = adsRepository.findAll()
                .stream()
                .filter(e -> e.getAuthor().getUsername().equals(currentUserName))
                .collect(Collectors.toList());
        List<Ad> adList = mapperUtil.convertListAdEntityToAd(adEntityList);
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

