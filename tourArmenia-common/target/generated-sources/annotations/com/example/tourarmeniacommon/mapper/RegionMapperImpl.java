package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.RegionDto;
import com.example.tourarmeniacommon.dto.RegionRequestDto;
import com.example.tourarmeniacommon.entity.Region;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-28T18:55:47+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class RegionMapperImpl implements RegionMapper {

    @Override
    public Region map(RegionRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Region region = new Region();

        region.setName( dto.getName() );
        region.setRegionalCenter( dto.getRegionalCenter() );

        return region;
    }

    @Override
    public RegionDto mapToDto(Region region) {
        if ( region == null ) {
            return null;
        }

        RegionDto regionDto = new RegionDto();

        regionDto.setId( region.getId() );
        regionDto.setName( region.getName() );
        regionDto.setRegionalCenter( region.getRegionalCenter() );

        return regionDto;
    }

    @Override
    public List<RegionDto> mapListToDtos(List<Region> all) {
        if ( all == null ) {
            return null;
        }

        List<RegionDto> list = new ArrayList<RegionDto>( all.size() );
        for ( Region region : all ) {
            list.add( mapToDto( region ) );
        }

        return list;
    }
}
