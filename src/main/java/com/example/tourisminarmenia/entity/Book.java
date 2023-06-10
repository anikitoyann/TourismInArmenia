package com.example.tourisminarmenia.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String city;
    private String phone;
    private String email;
    private String notes;

    @ManyToOne
    private TourPackage tourPackage;

    @ManyToOne
    private User user;


}
