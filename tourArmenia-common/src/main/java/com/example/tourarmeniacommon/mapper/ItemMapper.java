package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CreateItemRequestDto;
import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface ItemMapper {
    @Mapping(target = "region.id", source = "regionId")
    Item map(CreateItemRequestDto dto);

    @Mapping(target = "regionDto", source = "region")
    ItemDto mapToDto(Item entity);
}
