package com.db.cookies.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    String model;
}
