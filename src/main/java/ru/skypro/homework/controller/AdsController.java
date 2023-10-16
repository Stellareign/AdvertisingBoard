package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.MapperUtil;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.AdsImageService;
import ru.skypro.homework.service.interfaces.AdsService;

import java.io.*;

@Slf4j //  добавляет logger в класс
@CrossOrigin(value = "http://localhost:3000") // Позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
//@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/ads")
public class AdsController {

    public AdsController(AdsService adsService
//            , UserRepository userRepository
//            , MapperUtil mapperUtil
//            , AdsImageService adsImageService
    ) {
        this.adsService = adsService;
//        this.adsImageService = adsImageService;
    }

    private final AdsService adsService;
//    private final AdsImageService adsImageService;


    //****************************************************
    // получение всех объявлений
    //  ********* Без предусловий **********
    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public ResponseEntity<AdsDTO> getAdsDTO() {
        return ResponseEntity.ok().body(adsService.getAdsDTO());
    }
    @Operation(
            operationId = "createAd",
            summary = "Добавление нового объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
            }
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")

    @PostMapping
    public ResponseEntity<?> createAd(@RequestBody String title,   // 'заголовок объявления'
                                      int price,               // 'цена объявления'
                                      String description,            //'описание объявления'
                                      MultipartFile image) throws IOException             //'адрес картинки объявления'
    //                                      User author)          //'id автора объявления'
    {
        Ad newAd = adsService.createAd(title, price, description, image);
        if (newAd != null) {
            if (newAd.getAuthor() == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok().body(newAd);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // получение информации об объявлении
    @Operation(
            operationId = "getAdById",
            summary = "Получение информации об объявлении по id",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('USER') and @adsService.getAdById(#adId).email == authentication.principal.username")

    @GetMapping("/{adId}")
    public ResponseEntity<ExtendedAdDTO> getAdById(@PathVariable int adId) {
        return ResponseEntity.ok().body(adsService.getAdById(adId));
    }

    // удаление объявления по id
    @Operation(
            operationId = "removeAdById",
            summary = "Удалить объявление по id",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and" +
            "@adsService.getAdById(#adId).email == authentication.principal.username)")

    @DeleteMapping("/{adId}")
    public void removeAdById(@PathVariable int adId) {
        adsService.deleteAdsById(adId);
    }

    @Operation(
            operationId = "updateAd",
            summary = "Обновить объявление по id",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and" +
            "@adsService.getAdById(#adId).email == authentication.principal.username)")

    @PatchMapping("/{adId}")

    public ResponseEntity<Ad> updateAd(@PathVariable int adId, @RequestBody CreateOrUpdateAdDTO updateAd) {

        return ResponseEntity.ok().body(adsService.editAdById(adId, updateAd));
    }

    @Operation(
            operationId = "getCurrentUserAds",
            summary = "Получение всех объявлений авторизованного пользователя",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
//                    @ApiResponse(responseCode = "403", description = "Forbidden"),
//                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('USER') and @adsService.getAdById(#adId).email == authentication.principal.username")
    @GetMapping("/me")

    public ResponseEntity<AdsDTO> getCurrentUserAds(Authentication authentication) {
        return ResponseEntity.ok().body(adsService.getAllAdsByUser(authentication));
    }
    @Operation(
            operationId = "updateImage",
            summary = "Обновление картинки объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('USER') and @adsService.getAdById(#adId).email == authentication.principal.username")
    @PatchMapping(value = "/{adId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<AdsImage> updateImage(@PathVariable int adId,
                                                @RequestParam("image") MultipartFile image) throws IOException {

        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateImage(adId, image));
    }
}

