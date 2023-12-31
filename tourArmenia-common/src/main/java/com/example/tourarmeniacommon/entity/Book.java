package com.example.tourarmeniacommon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String notes;
    private int guestCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @ManyToOne
    private TourPackage tourPackage;
    @ManyToOne
    private Item item;
    @NotNull
    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;

    private boolean enabled;
    private String token;
}
