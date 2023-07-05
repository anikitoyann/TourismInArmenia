package com.example.tourarmeniacommon.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}