package com.example.tourarmeniacommon.dto;

import com.example.tourarmeniacommon.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSearchDto {
    private String name;
    private String description;
    private String regionName;
    private Type type;
    private String sortBy;
    private String sortDirection;
}
