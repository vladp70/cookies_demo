package com.db.cookies.services;

import com.db.cookies.dto.CarsDTO;
import com.db.cookies.models.Car;
import com.db.cookies.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public CarsDTO getAllCars(Boolean darkMode) {
        List<Car> cars = carRepository.findAll();
        CarsDTO result = new CarsDTO((darkMode != null && darkMode), cars);
        return result;
    }

    public Car saveCar(Car c) {
        return carRepository.save(c);
    }

    public Optional<Car> getCarById(Integer id){
        return carRepository.findById(id);
    }
}
