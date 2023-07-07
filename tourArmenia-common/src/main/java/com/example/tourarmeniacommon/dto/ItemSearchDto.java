package com.example.tourarmeniacommon.dto;

import com.example.tourarmeniacommon.entity.Type;
import lombok.Data;

@Data
public class ItemSearchDto {
    private String name;
    private String description;
    private String regionName;
    private Type type;
    private String sortBy;
    private String sortDirection;
}
