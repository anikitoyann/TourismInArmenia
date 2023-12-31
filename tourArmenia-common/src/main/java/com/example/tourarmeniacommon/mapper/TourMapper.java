package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CreateTourRequestDto;
import com.example.tourarmeniacommon.dto.TourDto;
import com.example.tourarmeniacommon.entity.TourPackage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemMapper.class, RegionMapper.class, CarMapper.class})
public abstract class TourMapper {
    @Value("${site.url}")
    String siteUrl;
    @Mapping(target = "item.id", source = "itemId")
    @Mapping(target = "car.id", source = "carId")
    @Mapping(target = "region.id", source = "regionId")
    public abstract TourPackage map(CreateTourRequestDto dto);

    @Mapping(target = "itemDto", source = "item")
    @Mapping(target = "carDto", source = "car")
    @Mapping(target = "regionDto", source = "region")
    @Mapping(target = "priceAmd", source = "price")
    @Mapping(target = "picUrl", expression = "java(entity.getPicName() != null ? siteUrl + \"/tours/getImage?picName=\" + entity.getPicName() : null)")
    public abstract TourDto mapToDto(TourPackage entity);
    public abstract List<TourDto> mapListToDtos(List<TourPackage> tours);

}