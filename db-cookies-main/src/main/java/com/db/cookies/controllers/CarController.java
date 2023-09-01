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
        ResponseCookie springCookie = ResponseCookie.from("darkMode", Boolean.toString(darkModeDTO.getDarkMode()))
                .path("/")
                .build();
        return ResponseEntity .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString()) .build();
    }

}
