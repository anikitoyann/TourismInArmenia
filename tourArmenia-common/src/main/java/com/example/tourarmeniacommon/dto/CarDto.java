package com.example.tourarmeniacommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private int id;
    private String name;
    private String seats;
    private String picUrl;
}
