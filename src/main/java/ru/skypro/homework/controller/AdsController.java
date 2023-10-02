package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.config.MapperUtil;
import ru.skypro.homework.dto.ads.AdsDTO;

import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.interfaces.AdsService;

import java.util.List;

@Slf4j //  добавляет логгер в класс
@CrossOrigin(value = "http://localhost:3000") // Позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull

@RestController
@RequestMapping("/ads")
public class AdsController {

    private final AdsService adsService;

    private final ModelMapper modelMapper;

    private final MapperUtil mapperUtil;


    //****************************************************
    // получение всех объявлений
    @GetMapping("/ads/comments/ads/{id}/comments")
//        @Operation(summary = "Получение всех объявлений")
    public List<AdsDTO> getAllAds() {
        List<Ad> adList = adsService.getAllAds();
        return mapperUtil.convertList(adList, this::convertToAdsDto);
    }

    // получение всех объявлений
    @GetMapping("/{adId}")
    @Operation(summary = "Получение объявления по id")
    public ResponseEntity<ExtendedAdDTO> getAdById(@PathVariable int adId) {
        ExtendedAdDTO extendedAdDTO = new ExtendedAdDTO();
        return ResponseEntity.ok().body(extendedAdDTO);
//        return convertToAdsDto(adsService.getAdsById(adId));
    }

    private AdsDTO convertToAdsDto(Ad ad) {
        AdsDTO adsDTO = modelMapper.map(ad, AdsDTO.class);
//            adsDTO.setAuthor(convertToAddUserDto(ad.getUser()));
        return adsDTO;
    }

    private UserDTO convertToAddUserDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    // добавление объявления
    public ResponseEntity<?> addAd(@RequestBody Ad ad) {
        if (ad != null) {
            return new ResponseEntity<>(adsService.addAd(ad), HttpStatus.CREATED); // код 201
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // удаление объявления по id
    @DeleteMapping("/{adId}")
    public ResponseEntity<String> deleteAdById(@PathVariable int adId) {
        boolean deleteAdById = adsService.deleteAdsById(adId);
        if (deleteAdById) {
            return new ResponseEntity<>(("Объявление с id = " + adId + "успешно удалено из базы"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(("Ошибка при попытке удалить Объявление с id = " + adId), HttpStatus.BAD_REQUEST);
        }
    }


    // обновление объявления
    @PatchMapping("/{adId}")
    //    public OwnerReport editOwnerReportById( long ownerReportId,
//                                            boolean photo,
//                                           String nutrition, String health, String behavior) {
//        return ownerReportService.editOwnerReportByIdFromController(ownerReportId, photo, nutrition, health, behavior);
//    }
    public ResponseEntity<CreateOrUpdateAdDTO> updateAd(int adId, @RequestParam String title, int price,
                                                        String description) {
        CreateOrUpdateAdDTO createOrUpdateAdDTO = new CreateOrUpdateAdDTO();
//            return ResponseEntity.ok(adsService.editAdById(adId,title, price, description));
        return ResponseEntity.ok().body(createOrUpdateAdDTO);
    }

}
