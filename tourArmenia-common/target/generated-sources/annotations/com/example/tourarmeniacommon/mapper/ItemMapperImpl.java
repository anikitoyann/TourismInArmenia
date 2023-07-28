package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CreateItemRequestDto;
import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-28T18:55:47+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class ItemMapperImpl extends ItemMapper {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public Item map(CreateItemRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Item.ItemBuilder item = Item.builder();

        item.region( createItemRequestDtoToRegion( dto ) );
        item.name( dto.getName() );
        item.description( dto.getDescription() );
        item.type( dto.getType() );

        return item.build();
    }

    @Override
    public ItemDto mapToDto(Item entity) {
        if ( entity == null ) {
            return null;
        }

        ItemDto itemDto = new ItemDto();

        itemDto.setRegionDto( regionMapper.mapToDto( entity.getRegion() ) );
        itemDto.setId( entity.getId() );
        itemDto.setName( entity.getName() );
        itemDto.setDescription( entity.getDescription() );
        itemDto.setType( entity.getType() );

        itemDto.setPicUrl( entity.getPicName() != null ? siteUrl + "/item/getImage?picName=" + entity.getPicName() : null );

        return itemDto;
    }

    @Override
    public List<ItemDto> mapListToDtos(List<Item> items) {
        if ( items == null ) {
            return null;
        }

        List<ItemDto> list = new ArrayList<ItemDto>( items.size() );
        for ( Item item : items ) {
            list.add( mapToDto( item ) );
        }

        return list;
    }

    protected Region createItemRequestDtoToRegion(CreateItemRequestDto createItemRequestDto) {
        if ( createItemRequestDto == null ) {
            return null;
        }

        Region region = new Region();

        region.setId( createItemRequestDto.getRegionId() );

        return region;
    }
}
