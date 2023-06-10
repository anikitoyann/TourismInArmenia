package com.example.tourisminarmenia.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String notes;

    @ManyToOne
    private TourPackage tourPackage;
    @ManyToOne
    private Hotel hotel;

    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;


}
