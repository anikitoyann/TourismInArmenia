package com.example.tourarmeniacommon.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourRequestDto {
    private String name;
    private int groupSize;
    private int price;
    private String duration;
    private Date startDate;
    private int regionId;
    private int carId;
    private int itemId;
}