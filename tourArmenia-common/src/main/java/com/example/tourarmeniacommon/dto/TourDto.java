package com.example.tourarmeniacommon.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDto {
    private int id;
    private String name;
    private String picUrl;
    private double priceAmd;
    private double priceUsd;
    private double priceRub;
    private String duration;
    private LocalDateTime startDate;
    private int groupSize;
    private CarDto carDto;
    private ItemDto itemDto;
    private RegionDto regionDto;
}