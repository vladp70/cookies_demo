package com.db.cookies.controllers;

import com.db.cookies.dto.CarsDTO;
import com.db.cookies.dto.DarkModeDTO;
import com.db.cookies.models.Car;
import com.db.cookies.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping("/cars")
    CarsDTO getCarsDTO(
            @CookieValue(name = "darkMode", required = false) Boolean darkMode
    ) {
        return carService.getAllCars(darkMode);
    }

    @PutMapping("/dark-mode")
    ResponseEntity<String> setDarkModeStatus(
            @RequestBody DarkModeDTO darkModeDTO) {

        return null;
    }

    @GetMapping("/coupon/{id}")
    public ResponseEntity<String> getCouponById(
            @PathVariable Integer id,
            @CookieValue(name = "code", defaultValue = "") String cookieCode
    ){
        Date current = new Date();
        Optional<Car> obj = carService.getCarById(id);

       if(obj.isEmpty() || obj.get().getExpiryDate().compareTo(current) < 0) {
           String errorMessage = "Not found!";
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage).;
       }

       // cuponul a fost gasit si nu este expirat
        // e prima data cand utilizatorul cere cuponul (nu are un cookie cu el)
        // sau are un cookie care este diferit de codeul cuponului
        if (cookieCode.compareTo("") == 0 || obj.get().getCode().compareTo(cookieCode) != 0) {
            // TODO: cresc numarul de utilizari ale cuponului
            Car car = obj.get();
            if (car.getNumUsesLeft() - 1 < 0) {
                String errorMessage = "No usages left!";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            car.setNumUses(car.getNumUses() + 1);
            car.setNumUsesLeft(car.getNumUsesLeft() - 1);
            carService.saveCar(car);
        } else {
            // Nu cresc numarul de utilizari
        }

        ResponseCookie springCookie = ResponseCookie.from("code", obj.get().getCode())
                .path("/")
                .build();
        return ResponseEntity .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString()) .build();
    }



}
