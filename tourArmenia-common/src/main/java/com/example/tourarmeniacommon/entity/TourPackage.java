package com.example.tourarmeniacommon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "name can't be null or empty")
    private String name;
    @Range(min = 0)
    private double price;
    @NotNull(message = "groupSize is not null")
    private int groupSize;
    @NotEmpty(message = "Duration can not be null")
    private String duration;
    private String picName;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @NotNull(message = "Region is not null")
    @ManyToOne
    private Region region;
    @ManyToOne
    private Car car;
    @ManyToOne
    private Item item;

}
