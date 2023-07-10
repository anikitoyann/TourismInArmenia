package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CreateItemRequestDto;
import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring", uses = RegionMapper.class)
public abstract class ItemMapper {
    @Value("${site.url}")
    String siteUrl;
    @Mapping(target = "region.id", source = "regionId")
    public abstract Item map(CreateItemRequestDto dto);

    //@Mapping(target = "regionDto", source = "region")
    @Mapping(target = "picUrl", expression = "java(entity.getPicName() != null ? siteUrl + \"/item/getImage?picName=\" + entity.getPicName() : null)")
    public abstract ItemDto mapToDto(Item entity);
    public abstract List<ItemDto> mapListToDtos(List<Item> items);

}
