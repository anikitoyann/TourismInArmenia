package com.example.tourarmeniacommon.dto;

import com.example.tourarmeniacommon.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequestDto {
    private String name;
    private String description;
    private int regionId;
    private Type type;

}
