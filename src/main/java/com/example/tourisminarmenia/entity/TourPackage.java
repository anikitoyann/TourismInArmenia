package com.example.tourisminarmenia.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "tour_package")
public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private int groupSize;
    private String duration;
    private String picName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @ManyToOne
    private Region regions;
    @ManyToOne
    private Car cars;
    @ManyToOne
    private Hotel hotel;

}
