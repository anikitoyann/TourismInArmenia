package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CreateTourRequestDto;
import com.example.tourarmeniacommon.dto.TourDto;
import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.TourPackage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-29T23:03:29+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class TourMapperImpl extends TourMapper {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private CarMapper carMapper;

    @Override
    public TourPackage map(CreateTourRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        TourPackage tourPackage = new TourPackage();

        tourPackage.setItem( createTourRequestDtoToItem( dto ) );
        tourPackage.setCar( createTourRequestDtoToCar( dto ) );
        tourPackage.setRegion( createTourRequestDtoToRegion( dto ) );
        tourPackage.setName( dto.getName() );
        tourPackage.setPrice( dto.getPrice() );
        tourPackage.setGroupSize( dto.getGroupSize() );
        tourPackage.setDuration( dto.getDuration() );
        tourPackage.setStartDate( dto.getStartDate() );

        return tourPackage;
    }

    @Override
    public TourDto mapToDto(TourPackage entity) {
        if ( entity == null ) {
            return null;
        }

        TourDto tourDto = new TourDto();

        tourDto.setItemDto( itemMapper.mapToDto( entity.getItem() ) );
        tourDto.setCarDto( carMapper.mapToDto( entity.getCar() ) );
        tourDto.setRegionDto( regionMapper.mapToDto( entity.getRegion() ) );
        tourDto.setPriceAmd( entity.getPrice() );
        tourDto.setId( entity.getId() );
        tourDto.setName( entity.getName() );
        tourDto.setDuration( entity.getDuration() );
        if ( entity.getStartDate() != null ) {
            tourDto.setStartDate( LocalDateTime.ofInstant( entity.getStartDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        tourDto.setGroupSize( entity.getGroupSize() );

        tourDto.setPicUrl( entity.getPicName() != null ? siteUrl + "/tours/getImage?picName=" + entity.getPicName() : null );

        return tourDto;
    }

    @Override
    public List<TourDto> mapListToDtos(List<TourPackage> tours) {
        if ( tours == null ) {
            return null;
        }

        List<TourDto> list = new ArrayList<TourDto>( tours.size() );
        for ( TourPackage tourPackage : tours ) {
            list.add( mapToDto( tourPackage ) );
        }

        return list;
    }

    protected Item createTourRequestDtoToItem(CreateTourRequestDto createTourRequestDto) {
        if ( createTourRequestDto == null ) {
            return null;
        }

        Item.ItemBuilder item = Item.builder();

        item.id( createTourRequestDto.getItemId() );

        return item.build();
    }

    protected Car createTourRequestDtoToCar(CreateTourRequestDto createTourRequestDto) {
        if ( createTourRequestDto == null ) {
            return null;
        }

        Car.CarBuilder car = Car.builder();

        car.id( createTourRequestDto.getCarId() );

        return car.build();
    }

    protected Region createTourRequestDtoToRegion(CreateTourRequestDto createTourRequestDto) {
        if ( createTourRequestDto == null ) {
            return null;
        }

        Region region = new Region();

        region.setId( createTourRequestDto.getRegionId() );

        return region;
    }
}
