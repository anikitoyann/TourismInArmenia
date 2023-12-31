package com.example.tourarmeniacommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionRequestDto {
    private String name;
    private String regionalCenter;
}
