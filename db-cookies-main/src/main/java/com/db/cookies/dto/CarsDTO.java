package com.db.cookies.dto;

import com.db.cookies.models.Car;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CarsDTO {
    Boolean darkMode;
    List<Car> cars;
}
