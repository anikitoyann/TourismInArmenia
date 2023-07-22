package com.example.tourarmeniacommon.dto;

import com.example.tourarmeniacommon.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private int id;
    private String name;
    private String picUrl;
    private String description;
    private Type type;
    private RegionDto regionDto;
}
