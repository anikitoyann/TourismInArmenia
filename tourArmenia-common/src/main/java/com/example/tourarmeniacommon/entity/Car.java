package com.example.tourarmeniacommon.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name can't be null or empty")
    private String name;
    @NotEmpty(message = "seats can't be null")
    private String seats;
    private String picName;
}
