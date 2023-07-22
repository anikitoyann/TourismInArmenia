package com.example.tourarmeniacommon.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private int groupSize;
    private String duration;
    private String picName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @ManyToOne
    private Region region;
    @ManyToOne
    private Car car;
    @ManyToOne
    private Item item;

}
