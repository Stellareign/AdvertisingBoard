package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.config.MapperUtil;

import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;
import java.util.List;

@Slf4j //  добавляет logger в класс
@CrossOrigin(value = "http://localhost:3000") // Позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
//@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/ads")
public class AdsController {

    public AdsController(AdsService adsService, UserRepository userRepository, MapperUtil mapperUtil) {
        this.adsService = adsService;
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    private final AdsService adsService;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;


    //****************************************************
    // получение всех объявлений
    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public ResponseEntity<AdsDTO> getAllAds() {
        List<Ad> adList = adsService.getAllAds();
        return ResponseEntity.ok().body(new AdsDTO(adList.size(), adList));
    }

    @PostMapping
    @Operation(summary = "Добавление нового объявления")
    public ResponseEntity<Ad> createAd(@RequestBody String title,   // 'заголовок объявления'
                                       int price,               // 'цена объявления'
                                       String image,            //'ссылка на картинку объявления'
                                       User author) {            //'id автора объявления'
        Ad newAd = adsService.addAd(title, price, image, author);
        if (newAd != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newAd);
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // получение информации об объявлении
    @GetMapping("/{adId}")
    @Operation(summary = "Получение информации об объявлении по id")
    public ResponseEntity<ExtendedAdDTO> getAdById(@PathVariable int adId) {
        Ad adById = adsService.getAdById(adId);
        if (adById == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        User authorAd = userRepository.findById(adById.getAuthor().getId()).orElseThrow();
//      if (authorAd == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok().body(mapperUtil.createExtendedAdDTO(adById, authorAd));
    }


    // удаление объявления по id
    @DeleteMapping("/{adId}")
    public ResponseEntity<?> removeAdById(@PathVariable int adId) {
        boolean removeIsOk = adsService.deleteAdsById(adId);
        if (removeIsOk) {
            return new ResponseEntity<>(("Объявление с id = " + adId + "успешно удалено из базы"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(("Ошибка при попытке удалить Объявление с id = " + adId), HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{adId}")
///ads/{id}
//Обновление информации об объявлении
//      "title": "string",
//              "price": 10000000,
//              "description": "string"
    public ResponseEntity<Ad> updateAd(@PathVariable int adId, @RequestParam String title, int price, String description) {
        CreateOrUpdateAdDTO updateAd = new CreateOrUpdateAdDTO(title, price, description);
        return ResponseEntity.ok(adsService.editAdById(adId, updateAd));
    }

    /*
    /ads/me
    Получение объявлений авторизованного пользователя
    На входе id юзера, на выходе кол-во и Лист его объявлений
      "count": 0,
      "results": [
        {
          "author": 0,
          "image": "string",
          "pk": 0,
          "price": 0,
          "title": "string"
        }
      ]
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Получение всех объявлений авторизованного пользователя")

    public ResponseEntity<AdsDTO> getAdsByUserId(@PathVariable int userId) {
        List<Ad> adList = adsService.getAllAdsByUser(userId);
        if (adList.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok().body(new AdsDTO(adList.size(), adList));
    }

    @PatchMapping("/{adId}/image")
    @Operation(summary = "Обновление картинки объявления")
    public ResponseEntity<Ad> editImageAdById(@PathVariable int adId, @RequestParam String imagePath) {
        if (adsService.getAdById(adId) == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if (imagePath.isEmpty()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok().body(adsService.editImageAdById(adId, imagePath));
    }
}

